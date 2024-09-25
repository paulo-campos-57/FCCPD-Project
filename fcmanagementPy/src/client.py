import threading
import subprocess
import platform
import os
import utils

def open_receiver_terminal(name, role):
    
   
    base_dir = os.path.join('FCCPD-Project', 'fcmanagementPy', 'src')

   
    receiver_file = "receiver.py"
    receiver_path = os.path.join(base_dir, receiver_file)

    receiver_path = os.path.abspath(receiver_path)

    if not os.path.isfile(receiver_path):
        print(f"Arquivo {receiver_file} não encontrado no diretório {base_dir}.")
        return
    
    command = f'python {receiver_path} {name} {role}'

    if platform.system() == "Windows":
        subprocess.Popen(f'start cmd /K "cd /d {base_dir} && {command}"', shell=True)
    elif platform.system() == "Linux":
        subprocess.Popen(f'gnome-terminal -- bash -c "cd {base_dir} && {command}; exec bash"', shell=True)
    elif platform.system() == "Darwin": 
        subprocess.Popen(f'open -a Terminal "{base_dir}" && {command}', shell=True)

def receive():
    while True:
        try:
            name = input("Enter your name (or 0 to exit): ")
            if name == "0":
                print("Exiting...")
                break
            
            utils.print_menu()
            
            choice = int(input("Enter your role in the club (1-6, or 0 to exit): "))
            if choice == 0:
                print("Exiting...")
                break
            
            roles = {1: "Player", 2: "Coach", 3: "Medical", 4: "Janitors", 5: "Social Media", 6: "Financial"}
            role = roles.get(choice)

            if role:
                terminal_thread = threading.Thread(target=open_receiver_terminal, args=(name, role))
                terminal_thread.start()
                utils.clear_terminal()
                print("Thread created successfully!")
            else:
                print("Invalid choice. Please try again.")

        except ValueError:
            print("Invalid input. Please enter a valid number.")

if __name__ == '__main__':
    receive()
