FROM java:8

# RUN apt-get update

# Install MySQL.
RUN \
  apt-get update && \
  DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server && \
  rm -rf /var/lib/apt/lists/* && \
  sed -i 's/^\(bind-address\s.*\)/# \1/' /etc/mysql/my.cnf && \
  sed -i 's/^\(log_error\s.*\)/# \1/' /etc/mysql/my.cnf && \
  echo "mysqld_safe &" > /tmp/config && \
  echo "mysqladmin --silent --wait=30 ping || exit 1" >> /tmp/config && \
  echo "mysql -e 'GRANT ALL PRIVILEGES ON *.* TO \"root\"@\"%\" WITH GRANT OPTION;'" >> /tmp/config && \
  bash /tmp/config && \
  rm -f /tmp/config

# Define mountable directories.
VOLUME ["/etc/mysql", "/var/lib/mysql"]

# Define working directory.
# WORKDIR /data

# Install Gradle
RUN wget https://services.gradle.org/distributions/gradle-2.10-bin.zip
RUN unzip gradle-2.10-bin.zip
RUN mv gradle-2.10 /opt/
RUN rm gradle-2.10-bin.zip

ENV GRADLE_HOME /opt/gradle-2.10
ENV PATH $PATH:$GRADLE_HOME/bin

WORKDIR /home
EXPOSE 8080
# ENTRYPOINT ["gradle","bootrun"]
# RUN gradle -v
# RUN gradle build
# RUN gradle run

ENTRYPOINT ["/bin/bash", "-c"]
CMD ["/home/startup.bash"]
COPY ./startup.bash ./
RUN chmod +x ./startup.bash
