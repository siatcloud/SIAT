from django.urls import path
from . import views


urlpatterns = [
    path('', views.index, name='index'),
    path('ntu', views.ntu_ar, name='ntu_ar'),
]
