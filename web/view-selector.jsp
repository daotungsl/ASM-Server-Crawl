<%--
  Created by IntelliJ IDEA.
  User: Daotu
  Date: 10/06/2019
  Time: 9:44 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ViewSelector</title>
</head>
<jsp:include page="include.jsp"/>

<body>
<div class="container ">
    <div class="row">
        <div class="col-12">
            <form action="#" method="post">
                <h1>Add Post</h1>
                <h3>or
                    <button type="button" id="btnGoAddPage" class="btn btn-primary">Add page</button>

or
                    <button type="button" id="btnGoApiArticle" class="btn btn-primary">API all Article</button>
                    <button type="button" id="btnGoApiCategory" class="btn btn-primary">API all Category</button>
                </h3>
                <div class="row">
                    <div class="col-6">
                        <div class="form-group ">
                            <label for="NameUrl">Url</label>
                            <input type="text" class="form-control" name="previewLink" id="NameUrl"
                                   placeholder="Enter url"
                                   value="https://vnexpress.net/the-gioi/du-luat-dan-do-den-trung-quoc-khien-nguoi-hong-kong-noi-gian-3936043.html">
                        </div>
                        <div class="form-group ">
                            <label for="TitleSelector">Title Selector</label>
                            <input type="text" class="form-control" name="titleSelector" id="TitleSelector"
                                   placeholder="Enter title selector" value=".title_news_detail">
                        </div>
                        <div class="form-group ">
                            <label for="DesSelector">Description Selector</label>
                            <input type="text" class="form-control" name="desSelector" id="DesSelector"
                                   placeholder="Enter description selector" value=".description">
                        </div>
                        <div class="form-group ">
                            <label for="ContentSelector">Content Selector</label>
                            <input type="text" class="form-control" name="contentSelector" id="ContentSelector"
                                   placeholder="Enter content selector" value=".content_detail">
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="form-group">
                            <label for="exampleFormControlTextarea1">Notes</label>
                            <textarea class="form-control rounded-0" id="exampleFormControlTextarea1"
                                      placeholder="You can paste the selector here to remember ......."
                                      rows="10"></textarea>
                        </div>
                    </div>
                </div>


                <p style="font-style: italic;">
                    <span style="background-color: #b21f2d; color: #fff ">Note</span>
                    Click Preview button to check to be ok before click Save Button.
                </p>
                <button type="button" id="btnPreview" class="btn btn-primary">Preview</button>
                <button type="reset" class="btn btn-dark ">Reset</button>

                <div class="modal" id="myModal">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <!-- Modal Header -->
                            <div class="modal-header">
                                <h2 class="modal-title" id="article-title"></h2>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>

                            <!-- Modal body -->
                            <div class="modal-body">
                                <h4 id="article-des"></h4>
                                <div id="article-content"></div>
                            </div>

                            <!-- Modal footer -->
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" id="btnSave" data-dismiss="modal">Post
                                </button>
                                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                            </div>

                        </div>
                    </div>
                </div>

                <script>
                    $('#btnPreview').click(function () {
                        var dataToSend = {
                            previewLink: $('[name="previewLink"]').val(),
                            titleSelector: $('[name="titleSelector"]').val(),
                            desSelector: $('[name="desSelector"]').val(),
                            contentSelector: $('[name="contentSelector"]').val(),
                        };
                        console.log(dataToSend);
                        $.ajax({
                            url: "/selector",
                            method: "POST",
                            data: JSON.stringify((dataToSend)),
                            success: function (data, status, xhr) {
                                $('#article-title').text(data.title);
                                $('#article-des').text(data.description);
                                $('#article-content').html(data.content);
                                $('#myModal').modal();
                            }
                        })
                    });
                    $('#btnGoAddPage').click(function () {
                        location.href = '/selector/page';
                    });
                    $('#btnGoApiArticle').click(function () {
                        location.href = '/api/article';
                    });
                    $('#btnGoApiCategory').click(function () {
                        location.href = '/api/categories';
                    });
                    $('#btnSave').click(function () {
                        var dataToSend = {
                            link: $('[name="previewLink"]').val(),
                            title: $('#article-title').text(),
                            description: $('#article-des').text(),
                            content: $('#article-content').text(),
                            status: 1
                        };
                        console.log(dataToSend);
                        $.ajax({
                            url: "/api/article",
                            method: "POST",
                            data: JSON.stringify(dataToSend),
                            success: function (data, status, xhr) {
                                $('#myModal').modal('hide');
                                alert("Success");

                            }
                        })
                    })
                </script>
            </form>
        </div>


    </div>
</div>
</body>
</html>
