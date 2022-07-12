# 更新yum
yum -y update

# 安装Docker
yum install -y docker

# 启动Docker
service docker start

# 开机自启动
# systemctl enable docker

# 下载加速器DaoCloud
curl -sSL https://get.daocloud.io/daotools/set_mirror.sh | sh -s http://f1361db2.m.daocloud.io

# 修改加速器配置文件
sed -i "s#,##g" /etc/docker/daemon.json

# 安装java环境
docker pull docker.io/java