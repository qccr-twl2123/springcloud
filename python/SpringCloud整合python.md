### SpringCloud整合python

* 主要目标
```text
1.Flask最优架构方案
2.Flask dockerfile 文件编写
3.Sidecar模块编写

```

* Sidecar简介
```text
1.Sidecar是一个用于监听非JVM应用程序(可以是Python或者Node或者Php等等)的一个工具，
   通过Sidecar可以实现Java和第三方应用程序的双向交互.
2.第三方应用程序必须要实现一个接口，实时向Sidecar报告自己的状态，告诉Sidecar自己还在运行着。
3.Sidecar应用程序必须和第三方应用程序运行在同一台电脑上，也就是说他们之间是localhost，不能是ip访问
```

* springboot整合sidecar教程

```xml
 <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-netflix-sidecar</artifactId>
      <version>1.4.7.RELEASE</version>
</dependency>
```

```yaml
spring:
  application:
    name: py-sidecar
server:
  port: 8001  #sidecar运行的端口
sidecar:
  port: 3000  #代表第三方程序运行的端口（比如上方的Python），所以监听端口为3000
  health-uri: http://localhost:${sidecar.port}/health #Python健康接口，应指向python的健康服务
```

* 注意点
```text
需要将sidecar服务与python服务部署在同一台机器
```

* python-flask 示例代码
```python

from flask import Flask, abort, request
from flask_restful import Resource, Api
from .utils import search_book


app = Flask(__name__)
api = Api(app)

LAST_ID = 33
books = [{
    'id': 33,
    'title': 'The Raven',
    'author_id': 1
}]


class BookResource(Resource):
    def get(self, book_id):
        book = search_book(books, book_id)
        if book is None:
            abort(404)
        return book

    def delete(self, book_id):
        for idx, book in enumerate(books):
            if book['id'] == book_id:
                del books[idx]
                return '', 204
        abort(404)


class BookListResource(Resource):
    def get(self):
        return books

    def post(self):
        global LAST_ID
        LAST_ID += 1
        data = request.json
        book = {
            'id': LAST_ID,
            'title': data['title'],
            'author_id': data['author_id']
        }
        books.append(book)
        return book, 201


api.add_resource(BookListResource, '/book')
api.add_resource(BookResource, '/book/<int:book_id>')


@app.errorhandler(404)
def not_found(e):
    return '', 404
```

[SpringCloud 整合Python](https://blog.csdn.net/LoveCarpenter/article/details/78819227)
[设计 RESTful API](http://www.pythondoc.com/flask-restful/second.html#flask-restful-restful-api)
[利用Python和Flask快速开发RESTful API](https://zhuanlan.zhihu.com/p/24629177)