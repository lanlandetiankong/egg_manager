```
一 删除.idea 文件夹
git rm -r --cached .idea  #--cached不会把本地的.idea删除
git commit -m 'delete .idea dir'
git push -u origin master
二 删除.iml文件(先手动删除iml文件,再操作如下指令即可)
git status                                       //查看当前状态，确认是否已经删除了iml文件
git pull                                           //不是必要步骤，只要能保证项目是最新的即可
git add .                                        //add后面有一个空格然后是 . 号，添加改变的文件
git commit -m 'delete iml file'         //提交到本地仓库
git push                                        //提交到远程仓库
```



未合并文件workspace.xml

```
git restore --staged  .idea/workspace.xml
```



```
一、git删除tag
1. 删除本地tag
git tag -d tag-name
2. 删除远程tag
git push origin :refs/tags/tag-name
```