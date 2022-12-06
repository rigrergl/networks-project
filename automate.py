import subprocess
import os
import time

def compile_java(file_name):
    os.system("javac " + file_name)

def run_server():
    cmd = 'start java TCPServer'
    proc = subprocess.Popen(cmd, shell=True)


def run_client(client_name): 
    time.sleep(0.1)
    cmd = 'start java TCPClient ' + client_name
    proc = subprocess.Popen(cmd, shell=True)

compile_java("TCPServer.java")
compile_java("TCPClient.java")

run_server()
run_client("client_1")
run_client("client_2")
run_client("client_3")
