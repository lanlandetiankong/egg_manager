@echo off
::�˿ڹ���
:main
echo ------------------------------
echo 1.ȫ���˿��б�
echo 2.��ѯ�˿���Ϣ
echo 3.��ѯPID��Ӧ����
echo 4.����˿�ռ��
echo ------------------------------
	echo.
set /p X=��������ѡ����:

if %X%==1 (
	goto findAllPort
)else if %x%==2 (
goto mPort
)else if %x%==3 (
goto findPid
)else if %x%==4 (
goto killPort
)

:findAllPort
cls
	echo.	
	netstat -aon
	echo.
	goto main
	
:mPort
cls
    set /p mport=������Ҫ���ҵĶ˿�:
	echo.	
	echo   Э��   ���ص�ַ          	�ⲿ��ַ        	״̬           PID
	netstat -aon|findstr %mport%
	echo.
	goto main

:findPid
cls
    set /p mPid=������˿ڶ�Ӧ��PID��:
	tasklist|findstr %mPid%
	echo.
	goto main
	
:killPort
cls
set /p mPid=��������Ҫ����˿ڶ�Ӧ��PID��:

taskkill /f /t /pid %mPid%
	echo.
	goto main