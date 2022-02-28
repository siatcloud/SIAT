from django.shortcuts import render
from django.http import JsonResponse
from django.contrib.staticfiles.storage import staticfiles_storage
from .agcn import Model as ARModel

import torch
from torch.autograd import Variable
import os
import sys

from acRecApi.graph.ntu_rgb_d import Graph
from acRecApi.data_gen import ntu_gendata as gendata


def index(request):
    context = {}
    return render(request, 'acRecApi/index.html', context)


def ntu_ar(request):

    data_path = request.GET.get('data', '')

    model_dir = './acRecApi/static/models/ntu/'
    joint_path = model_dir + 'ntu_cv_agcn_joint-49-58800.pt'
    bone_path = model_dir + 'ntu_cv_agcn_bone-49-58800.pt'
    data_path = model_dir + data_path

    resp_data = {'status': 0, 'message': 'successful',
                 'predict_value': [], 'predict_label': []}

    try:
        # select GPU
        device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")

        # Creating model using model class and model file
        ar_model = ARModel(
            graph='acRecApi.graph.ntu_rgb_d.Graph').to(device=device)
        ar_model.load_state_dict(torch.load(joint_path))
        ar_model.load_state_dict(torch.load(bone_path))

        # Generating test data
        data = [gendata.read_xyz(data_path)]
        data = torch.tensor(data).to(device=device)
        data = Variable(data.float().cuda(device),
                        requires_grad=False, volatile=True)

        # predicting output
        output = ar_model(data)

        value, predict_label = torch.max(output.data, 1)

        resp_data['predict_value'] = value.tolist()
        resp_data['predict_label'] = predict_label.tolist()

    except Exception as e:
        resp_data['status'] = 1
        resp_data['message'] = str(e)

    return JsonResponse(resp_data, safe=False)
