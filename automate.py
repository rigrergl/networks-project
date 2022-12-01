import subprocess
def compile_java(java_file):
    # cmd = '/path/to/javac/javac ' + java_file 
    cmd = 'C:/\"Program Files\"/Java/jdk-19/bin/javac.exe ' + java_file 
    proc = subprocess.Popen(cmd, shell=True)

compile_java("TCPServer.java")
compile_java("TCPClient.java")