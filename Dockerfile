FROM ubuntu:latest
LABEL authors="raph"

ENTRYPOINT ["top", "-b"]