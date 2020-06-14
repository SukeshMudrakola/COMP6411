import socket
import sys

s = socket.socket()
host = socket.gethostname()
port = 9999

s.connect((host, port))
print('Connected to', host)

while True:

    #print console
    print("Python DB Menu \n 1. Find customer \n 2. Add customer \n 3. Delete customer \n 4. Update customer age \n 5. Update customer address \n 6. Update customer phone \n 7. Print report \n 8. Exit")


    z = input("Select any of the option [1-8] from the above menu: ")
    if(z=="1"):
        name=input("enter the name of the person to find: ")
        opt = z + "|" +name
        s.send(opt.encode())

    elif(z=="2"):
        name=input("enter the name of the person to enter: ").strip()
        if not name:
            print("Name cannot be empty")
            continue
        else:
            age= input("enter the age of the person: ").strip()
            if(age.isdigit()) or not age:
                address= input("enter the address of the person: ")
                phoneNumber = input("enter the phone number of the person: ")
                opt = z + "|" +name + "|" + age +  "|" + address +  "|" + phoneNumber  
                s.send(opt.encode())
            else:
                print("age needs to be a number")
                continue

    elif(z=="3"):
        name=input("enter the name of the person to delete: ").strip()
        if not name:
            print("Name cannot be empty")
            continue
        else:
            opt = z + "|" +name
            s.send(opt.encode())
    elif(z=="4"):
        name=input("enter the name of the person to update age: ").strip()
        if not name:
            print("Name cannot be empty")
            continue
        else:
            age= input("enter the new age of the person: ").strip()
            if(age.isdigit() or not age):
                opt = z + "|" +name + "|" + age
                s.send(opt.encode())
            else:
                print("Age needs to be a number")
                continue
    elif(z=="5"):
        name=input("enter the name of the person to update address: ").strip()
        if not name:
            print("Name cannot be empty")
            continue
        else:
            address= input("enter the new address of the person: ")
            opt = z + "|" +name + "|" + address
            s.send(opt.encode())
    elif(z=="6"):
        name=input("enter the name of the person to update Phone Number: ").strip()
        if not name:
            print("Name cannot be empty")
            continue
        else:
            phone= input("enter the new phone Number of the person: ")
            opt = z + "|" +name + "|" + phone
            s.send(opt.encode())
    elif(z=="7"):
        opt=z
        s.send(opt.encode())
        print("The information of the customer DB is as follows ")
        print("---------------------------------------------------------------------")
        print("Name                |Age |Address                  |Phone Number")
    elif(z=="8"):
        opt=z
        s.send(opt.encode())
        print("goodbye")
        sys.exit()
    else:
        print("invalid option")
        continue

    
    
    # Halts
    #print('[Waiting for response...]')
    print("---------------------------------------------------------------------")
    
    print((s.recv(1024)).decode("utf-8"))
    
    print("---------------------------------------------------------------------")