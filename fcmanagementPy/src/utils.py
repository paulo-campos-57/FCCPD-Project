import os


def clear_terminal():
    if os.name == 'nt':
        os.system('cls')
    else:
        os.system('clear')


def print_menu():
    print("Roles menu\n")
    print("1 - Players")
    print("2 - Coaches")
    print("3 - Medical")
    print("4 - Janitors")
    print("5 - Social Media")
    print("6 - Financial")
