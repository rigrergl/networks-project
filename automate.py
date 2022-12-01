import subprocess
def compile_java(java_file):
    cmd = '/path/to/javac/javac ' + java_file 
    # cmd = 'C:/\"Program Files\"/Java/jdk-19/bin/javac.exe ' + java_file # TODO: automate javac location
    proc = subprocess.Popen(cmd, shell=True)

def run_server():
    cmd = 'start java TCPServer'
    # shell = subprocess.Popen("start cmd", shell=True)
    proc = subprocess.Popen(cmd, shell=True)


def run_client(client_name): 
    cmd = 'start java TCPClient ' + client_name
    # shell = subprocess.Popen("start cmd", shell=True)
    
    proc = subprocess.Popen(cmd, shell=True)

compile_java("TCPServer.java")
compile_java("TCPClient.java")

run_server()
# run_client("joe")

input('Enter to exit from Python script...')