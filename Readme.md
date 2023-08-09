## Dockerization
``` 
Docker is a tool designed to make it easier to create, deploy, and run applications by using containers.
    
    Before understanding docker we have to understand the older concepts of virtualization. it is an older concepts 
        like banks, universities, and other organizations their infrastructure is running on virtualization.
        
        Ques:-  What are virtual machine is ?
        Ans :-  virtual machine is bascially emulating the hardware resources that computer basically provides so it making emulating that resource in separate 
                  isolated environment. and we can spin up multiple virtual machine on a single hardware machine.
        Ques:-  Reason that Virtual machine is exists ?
        Ans :-  becoz in data centers || in clouds what happens is that, it cheaper to run very high powered machine, 
                    like machine with 32 core cpu, 128 core cpu, at home we have 4 core cpu, 8 core cpu, 16 core cpu,
                    Ram is like 8gb, 16gb and so on. 
                    but at data cneter level if we have to create data center of ourself then in the serverx will use there 
                        it is cheapter if we buy AMD Thread Wrapper kind of CPU's or intel Xeon kind of CPU's, agin it is cheaper 
                        if we use 100TB of disk, Ram also cheaper if we use D-RAM, synchronous RAM, that have larger array of RAM 
                        like 1 TB of RAM itself.
                    so if we make system like that then per uint MB, if we calculate the cost then it is cheaper.
                    therefor making bigger machine is cheaper 
                    but who deployed his server that might not always need the bigger machine
                    like small start up or small company, they create small server in cloud, they just need may be 2-Cores CPU power
                         and 2GB or 4GB of RAM or 10 GB of disk space, so the cloud provider what they prefer it, cloud provider can 
                         create a VM with our specification. and makes such 100 || 200 VM on a Server (it have very powerful) and rent it to us. 
                         
                         as user we feel like we have dedicated servers but actually we are using the shared resources.
                    Virtual Machines make that possible.
        VM basically run on Hypervisor, 
            What Hypervisor does ?
                it provide disk-space, Ram-space, CPU-space, and other resources to each VM on top of the Hypervisor. 
                so whatever we allocated to VM that are dedicated to VM, and it is not shared with other VM's.
        Drawbacks of VM
                    -   if we do this Dedicated allocation then even if the VM internally the load is not 100% 
                            let say no. of VM i can create in a system that " I can't have the Dynamically Based on load"
                                becoz i am allocating the resources dedicatedly to each VM.
                                even sometimes the VM are not running my resources are still blocked. 
                        more dynamic allocation which ever VM is taking less resources then rest of VM's can use that resources 
                            at run time that is not possible in VM's.
                       
                    -   becoz the entire system boots & OS boots up is taking time, turnning on virtual machine || turning off virtual machine 
                            is taking time, 
      Thats where another concept comes in the companies like GOOGLE, IBM ... 
        who came up with linux container, 
            which is extension of the system called CH groups.
            so CH groups being extented into LXC (linux container) 
                and then LXC is being extented into Docker.
                docker was not made inside google but it was made as a community open source project.
                    but lot of google distributed engineers were part of that community.
                    
    Docker
        Ques:-  What Docker does ?
        Ans :-  Docker does not create entire GuestOS & does not emulate the entire hardware.
                instead, the docker "lets application run on the docker engine & each application feels like it is running on separate OS,
                    they get dedicated disk-space" so if we have two docker apps running on the computer then each of them think that they 
                    are running on separate OS, they are running on separate disk-space, they don't see the data from each other. 
                    so the disk-space & internal memory is isolated. but "They ARTED CPU & RAM"
                so whenever they make OS calls like any user space thing like JVM || Node js InterpreE NOT RUNNING ON DEIDCAter || whatever program is run 
                     then these will make some OS calls (like store the file in disk, read data from the file). whatever OS calls they make 
                     they go via the docker engine to host os & the docker engine manages the isolation, the resource allocation does not need 
                     to be dedicated. but we can set up the upper limit so that if we set like 1 gb ram is reached then the particular docke app
                        should be shut down.
                     but when they are booting up & assume they takes 10 mb each then rest ram is available for the host os. 
    
        Pros:-
            -   becoz the entire guest os does not gone booting up on docker so it is much faster to start docker based app.
        Cons:-
            -   the isolation is less. 
            
        Common between VM & Docker
                    In VM if we running some maching that we stop it then capture an image of the disk & ram as it & save it 
                        that image can be used later on. || we can distribute that image to other people.
                        like if we build an server 
                               so we create an VM image, & inside that if we installed java, download the source code of the project and so on. 
                               we built it & run it. make it as an image & distribute it to other servers that we have.
                    the same benefits availabe with Docker images.
                        so wherever we have distributed there they don't need to install java , download source core & don't need to build it.
                        they just have to docker in it.
        
        Docker image is basically LINUX docker image. so if we installed docker & run that means there is some VM running 
            so one VM is runs linux top of windows & inside that docker is running becoz docker is linux based.   
            
            so we can't create windows or mac docker containers  the guest os can't be our choice, becoz docker is designe to run on linux. 
                as a result if we run docker in windows or mac then it is fairly slow, docker is only benefits when we actually deployed with cloud 
                where most server are based on linux.
            
            docker image are small in size becoz the OS is not inside the image. becoz docker directly calls the host os.
                so each image can't copy of the OS. so the image size is small.

        Working of Docker:-
                Virtual Machine based Memory Isolation:-
                        there is an RAM of 4GB, If we using hyperviser layaer,  
                            what it would do is ?
                                assume we created two VM's on hyperviser then hyperviser will create dedicated memory access on them
                                so from VM2 if we accessing the memory 000 that we can see in the picture. same with VM1. that how 
                                the memory dedicated like this.
                Docker:-
                        there is an RAM of 4GB, if we use docker engine to allocating the memory.
                            what happens is ?
                               two apps run but app1 boot up so initially they takes less memory. app2 is running so it require little bit more memory.
                                so first chunk of ram create & allocated to app1, second chunk of ram is allocated to app2.
                                In app1, after running for a while, app1 nees more memory so docker engine will create another chunk of memory (third)
                                & allocated to the other part of app1 so like that docker engine will allocate the memory dynamically.
                                after some time the other part of app1 required less memory so it reduce the size & then docker engine will 
                                reduce the size that how  memory is not dedicated to each app.             






```
### Useful links (must read)
1. https://www.weave.works/blog/a-practical-guide-to-choosing-between-docker-containers-and-vms
2. https://cloudacademy.com/blog/docker-vs-virtual-machines-differences-you-should-know/