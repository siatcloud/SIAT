import mysql.connector
import subprocess
import os


def get_user_info(email, username):
    mydb = mysql.connector.connect(
        host="localhost",
        user="root",
        password="",
        database="siat"
    )

    mycursor = mydb.cursor()
    swl = "SELECT * FROM `cctv_info` WHERE `username`='AnwarAbir' AND `institute_name`='khu'"

    sql = "SELECT * FROM `user_info` WHERE `username`='"+ username +"' "
    mycursor.execute(sql)

    myresult = mycursor.fetchall()

    for x in myresult:
        print(x)


def test():
    subprocess.call("php C:/xampp/htdocs/siat/admin/HTML/model/login_response.php", shell=True)
    proc = subprocess.Popen("php C:/xampp/htdocs/siat/admin/HTML/model/login_response.php", shell=True, stdout=subprocess.PIPE)
    script_response = proc.stdout.read()
    # print(script_response)
    encoding = 'utf-8'
    result = script_response.decode(encoding).split()
    print(result[4])
    # php = PHP("require '../code/private/common.php';")
    # code = """for ($i = 1; $i <= 10; $i++) { echo "$i\n"; }"""
    # print
    # php.get_raw(code)


def validate_user(username="", password=""):
    user_info = open('test_user.csv', "w+")
    user_info.write(username + ","+ password)
    user_info.close()

    subprocess.call("php C:/xampp/htdocs/siat/admin/HTML/model/login_response.php", shell=True)

    valid_info = open('validate.csv')
    information = valid_info.read()
    print("User validation: " + information)
    valid_info.close()
    return information


def get_cctv(username, institute):
    if (not institute):
        return ["Provide a valid institute name"]
    if (not username):
        return ["Provide a valid username"]

    mydb = mysql.connector.connect(
        host="localhost",
        user="root",
        password="",
        database="siat"
    )
    mycursor = mydb.cursor()
    sql = "SELECT * FROM `cctv_info` WHERE `username`='" + username + "' AND `institute_name`='" + institute + "'"
    mycursor.execute(sql)
    myresult = mycursor.fetchall()
    output = " "
    for x in myresult:
        eachIP = " " .join(str(x)).split(",")
        cctvIP = eachIP[1].replace(" ", "")
        output += cctvIP + ", "
    return output


def getVideoList(institute, username, dataset_name):
    if(not institute):
        return ["Provide a valid institute name"]
    if(not username):
        return ["Provide a valid username"]
    if(not dataset_name):
        return ["Provide a valid dbname"]

    path = "D:/siat/" + institute + "/" + username + "/" + dataset_name
    videoList = []
    print(path)
    for dirname, dirnames, filenames in os.walk(path):
        videoList = filenames
    return videoList



if __name__ == '__main__':
    email = "abir@dke.khu.ac.kr"
    username = "AnwarAbir"
    institute = "khu"
    dbname = "stad"
    password = "123"
    # validate_user(username, password)
    # get_user_info(email, username)
    # test()
    # filenames = getVideoList(institute, username, dbname)
    # for filename in filenames:
    #     print(filename)
    cctv_info = get_cctv(username, institute)
    print(cctv_info)
    # for x in cctv_info:
    #     print(x)