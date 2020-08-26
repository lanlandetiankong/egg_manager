请复制 EggManager_Service.exe,EggManager_Service.xml 到服务器上jar包同级目录

1、cmd命令
注册服务：
EggManager_Service.exe install
启动服务：
EggManager_Service.exe start
关闭服务：
EggManager_Service.exe stop
卸载服务：
EggManager_Service.exe uninstall
重启服务：
EggManager_Service.exe restart
2、bat方式(复制bat文件到jar包同级目录,由于注册服务与卸载服务很少使用，请直接使用cmd执行命令)
启动服务
Start_EggManagerServer.bat
重启服务
ReStart_EggManagerServer.bat
关闭服务
Stop_EggManagerServer.bat
