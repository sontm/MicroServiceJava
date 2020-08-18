# Very First, Each PC need install python, ssh, vim

# First, install Ansible. This need python
sudo apt-add-repository ppa:ansible/ansible
sudo apt update
sudo apt install ansible

# Configure SSH access to the server, passphrase can be 123456
ssh-keygen

# setup /etc/hosts file
#  127.0.1.1       kmaster
#  192.168.1.103   kworker1


#cat ~/.ssh/id_rsa.pub
#Here's what you do with the output of that command:
# Copy the text from the key.
# Log into your node server.
# Issue the command sudo -s.
# Open the authorized_keys file with the command sudo nano ~/.ssh/authorized_keys.
# Paste the contents of the server key at the bottom of this file.
# Save and close the file.
ssh-copy-id kmaster
ssh-copy-id kworker1


# Test connection
ansible -i hosts -m ping all

#------------------------------------------------------------------------
# Step 1: Prepare sudo password, users
ansible-playbook -i hosts 1-initial.yaml --ask-become-pass
	# We will enter Sudo Pass here


# Step 2: Install kubernetes
#   version: curl -s https://packages.cloud.google.com/apt/dists/kubernetes-xenial/main/binary-amd64/Packages | grep Version | awk '{print $2}'
ansible-playbook -i hosts 2-installkube.yaml


# Step 3: Setup Cluster on master
ansible-playbook -i hosts 3-setupcluster.yaml


# Step 4; Workers join
ansible-playbook -i hosts 4-workerjoin.yaml

# Step 5: Setup dashboard, 
#   Optional To FIx CrashLoopBackOff of coredns, DELETE loop in the command :$ kubectl edit cm coredns -n kube-system 




# Fix Flannel cni already have ip..., for every node and master
//sudo ifconfig cni0 down    
//sudo ip link delete cni0
//sudo brctl delbr cni0  

sudo ifconfig cni0 down && sudo rm -rf /var/lib/cni/flannel/* && sudo rm -rf /var/lib/cni/networks/cbr0/* && sudo ip link delete cni0 && sudo rm -rf /var/lib/cni/networks/cni0/* 




# Disable GUI Ubuntu

To boot to console:

$ sudo systemctl set-default multi-user.target

You must then edit /etc/default/grub by removing splash from the GRUB command line. (Remember to update GRUB afterward: sudo update-grub).

To get to the Unity desktop from the console, you must enter the command:

$ sudo systemctl start lightdm.service
(The usual startx command doesn't work with Unity.)

To restore boot to GUI:

$ sudo systemctl set-default graphical.target
