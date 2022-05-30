#!/usr/bin/env sh

# abort on errors
set -e

# 推送代码
#git pull
#git add .
#git commit -m 'synchronize to Gitee master'

# gitee
git push -f "git@gitee.com:meidanlong/all-in-one.git" master:master