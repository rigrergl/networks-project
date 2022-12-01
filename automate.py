import subprocess
def compile_java(java_file):
    # cmd = '/path/to/javac/javac ' + java_file 
    cmd = 'C:/\"Program Files\"/Java/jdk-19/bin/javac.exe ' + java_file 
    proc = subprocess.Popen(cmd, shell=True, creationflags=subprocess.CREATE_NEW_CONSOLE)

def run_server():
    cmd = 'java ' + "TCPServer"
    proc = subprocess.Popen(cmd, shell=True)


def run_client(client_name): 
    cmd = 'java TCPClient ' + client_name
    proc = subprocess.Popen(cmd, shell=False)

compile_java("TCPServer.java")
compile_java("TCPClient.java")

run_server()
run_client("joe")