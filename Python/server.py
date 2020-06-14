import socket

s = socket.socket()
host = socket.gethostname()
port = 9999
s.bind((host, port))

s.listen(1)
c = None
dictdata = {}


def load():
        
        filename="data.txt"
        f=open(filename,"r")
        lines=f.readlines()

        for line in lines:
            data=line.split('|')
            #print(line)
            dictdata[data[0].lower()]=[temp.strip() for temp in data]
        f.close()
        
        
        
load()




while True:
   if c is None:
       # Halts
       print('[Waiting for connection...]')
       c, addr = s.accept()
       print('Got connection from', addr)
   else:
       # Halts
       print('[Waiting for response...]')

       # manupilating input received from client
       ipt=c.recv(1024)
       
      # print(dictdata[ipt.decode()])


      # print(c.recv(1024))
     #  q = input("Enter something to this client: ")
       if(ipt.decode().split("|")[0]=="1"): 
           print(ipt.decode().split("|")[1].lower())
           if(ipt.decode().split("|")[1].lower() in dictdata):
               lab="Customer details contains Name, Age, Address, phone number \n"
               c.send(lab.encode()+(str(dictdata[ipt.decode().split("|")[1].lower()]).strip('[]').encode()))
           else:
               c.send(str("Customer does not exists").encode())

       elif(ipt.decode().split("|")[0]=="2"): 
           if(ipt.decode().split("|")[1].lower() in dictdata):
               c.send(str("Customer already exists").encode())
           else:
               dictdata[str(ipt.decode().split("|")[1].lower()).strip()]=[ str(ipt.decode().split("|")[1]).strip(),str(ipt.decode().split("|")[2]).strip() , str(ipt.decode().split("|")[3]).strip() , str(ipt.decode().split("|")[4]).strip() ]
               
               c.send(str("Customer " + ipt.decode().split("|")[1] +" created").encode())

       elif(ipt.decode().split("|")[0]=="3"):
           if(ipt.decode().split("|")[1].lower() in dictdata):
               del dictdata[ipt.decode().split("|")[1].lower()]
               c.send(str((ipt.decode().split("|")[1]) + " has been deleted").encode())
           else:
                c.send(str("Customer does not exists").encode())
           
        
       elif(ipt.decode().split("|")[0]=="4"):
           if(ipt.decode().split("|")[1].lower() in dictdata):
               dictdata[ipt.decode().split("|")[1].lower()][1]=ipt.decode().split("|")[2]
               c.send(str((ipt.decode().split("|")[1])+"'s age has been updated").encode())
           else:
               c.send(str("Customer does not exists").encode())

       elif(ipt.decode().split("|")[0]=="5"):
            if(ipt.decode().split("|")[1].lower() in dictdata):
                 dictdata[ipt.decode().split("|")[1].lower()][2]=ipt.decode().split("|")[2]
                 c.send(str((ipt.decode().split("|")[1])+"'s address has been updated").encode())
            else:
                c.send(str("Customer does not exists").encode())

       elif(ipt.decode().split("|")[0]=="6"):
           print(ipt.decode().split("|")[1].lower())
           if(ipt.decode().split("|")[1].lower() in dictdata):
               dictdata[ipt.decode().split("|")[1].lower()][3]=ipt.decode().split("|")[2]
               c.send(str((ipt.decode().split("|")[1])+"'s phone number has been updated").encode())
           else:
               c.send(str("Customer does not exists").encode()) 

       elif(ipt.decode()=="7"):
            ss=""
            k=""
            sd = sorted(dictdata.items())
            for key, value in sd :
                 k=str(value[0]).strip().ljust(20)+'|' +str(value[1]).strip().ljust(4)+'|' +str(value[2]).strip().ljust(25)+'|' +str(value[3]).strip().ljust(12)+'|'
                 ss=ss+"\n"+k
            #print(s)
            c.send(str(ss).encode())

       elif (ipt.decode()=="8"):
            """ filename="data.txt"
            f=open(filename,"r+")
            f.truncate(0)
            f.close()
            f= open(filename,"w+") 
            delim='|'
            for line in dictdata.values():
                templine=delim.join(line)
                if ("\n" in templine):
                    f.write(templine)
                else:
                    f.write(templine+"\n")

            f.close() """
            c=None

    



           
        