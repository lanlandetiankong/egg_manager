@echo off
title EggManager-Service
::使用utf8编码
chcp 65001
::变量声明
SET district=D:
SET baseDir=D:\zcj\studys\projects\egg_manager
SET moduleName=egg-services
SET targetName=\target
SET jarName=EggManager-Service.jar

::拼接最终变量
SET fullPath=%baseDir%\%moduleName%%targetName%
echo "----------------提示----------------"
echo %fullPath%
echo "Begin->正在切换到jar包路径: "
cd %district%
cd %fullPath%
echo "Success->已切换到jar包路径: "%fullPath%
echo %cd%
echo "Begin->开始运行jar: "%jarName%
echo;
echo;
java -Dloader.path=.\lib -jar %jarName%
echo;
echo;
echo "Success->jar运行完毕.... "
echo;
echo "Waiting->正在等待jar包启动完毕，点击任何按键可退出界面! "
pause;