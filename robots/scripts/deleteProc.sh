# Get all selenium process info list.
IFS=$'\n'
proc_info_list=(`ps aux | grep $1| grep -v grep`)
echo `ps aux | grep $1| grep -v grep` >> deleteProc1.tmp

# Fetch pid and kill each selenium process
for proc in ${proc_info_list[@]}; do
    IFS=' '
    proc_info=($proc)
    echo ${proc_info[1]}
    echo ${proc_info[1]} >> deleteProc2.tmp 
    kill -9 ${proc_info[1]}
done
