<%@ page import="com.asm.java.entity.Article" %>
<%@ page import="java.util.*" %><%--
  Created by IntelliJ IDEA.
  User: Daotu
  Date: 10/06/2019
  Time: 2:58 CH
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
                <h1>Add Page</h1>
                <h3>or  <button type="button" id="btnGoAddPost" class="btn btn-primary">Add Post</button>
                or
                    <button type="button" id="btnGoApiArticle" class="btn btn-primary">API all Article</button>
                    <button type="button" id="btnGoApiCategory" class="btn btn-primary">API all Category</button>
                </h3>
                <div class="row">
                    <div class="col-6">
                <div class="form-group ">
                    <label for="NameUrl">Url Page</label>
                    <input type="text" class="form-control" name="urlPage" id="NameUrl"
                           placeholder="Enter url"
                           value="https://vnexpress.net/">
                </div>
                <div class="form-group ">
                    <label for="TitleSelector">Title Selector</label>
                    <input type="text" class="form-control" name="titleSelector" id="TitleSelector"
                           placeholder="Enter title selector" value=".title_news_detail">
                </div>
                <div class="form-group ">
                    <label for="LinkSelector">Link Selector</label>
                    <input type="text" class="form-control" name="linkSelector" id="LinkSelector"
                           placeholder="Enter link selector" value=".list_news .title_news a">
                </div>
                <div class="form-group ">
                    <label for="ImgSelector">Image Selector</label>
                    <input type="text" class="form-control" name="imgSelector" id="ImgSelector"
                           placeholder="Enter image selector" value=".list_news .thumb_art img">
                </div>
                <div class="form-group ">
                    <label for="DesSelector">Description Selector</label>
                    <input type="text" class="form-control" name="desSelector" id="DesSelector"
                           placeholder="Enter description selector" value=".sidebar_1 .description">
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
                                      rows="20"></textarea>
                        </div>
                    </div>
                </div>
                <button type="button" id="btnGo" class="btn btn-primary">Go</button>
                <button type="reset" class="btn btn-dark ">Reset</button>

                <div class="modal" id="myModal">
                    <div class="modal-dialog">
                        <div class="modal-content">

                            <!-- Modal Header -->
                            <div class="modal-header">
                                <h4 class="modal-title">
                                    Are you sure you want clone page : <span id="url-page" > </span>
                                </h4>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>

                            <!-- Modal body -->
                            <div class="modal-body" >
                                <h3>Link Selector: <span style="background-color: black; color: white ; padding: 3px" id="link-selector"></span> </h3>
                                <h3>Title Selector: <span style="background-color: black; color: white ;padding: 3px" id="title-selector"></span> </h3>
                                <h3>Description Selector: <span style="background-color: black; color: white ;padding: 3px" id="des-selector"></span> </h3>
                                <h3>Image Selector: <span style="background-color: black; color: white ;padding: 3px" id="img-selector"></span> </h3>
                                <h3>Content Selector: <span style="background-color: black; color: white ;padding: 3px" id="content-selector"></span> </h3>
                            </div>

                            <!-- Modal footer -->
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" id="btnSave" data-dismiss="modal">YES</button>
                                <button type="button" class="btn btn-danger" data-dismiss="modal">NO</button>
                            </div>

                        </div>
                    </div>
                </div>

                <script>
                    $('#btnGoAddPost').click(function () {
                        location.href = '/selector';
                    });
                    $('#btnGoApiArticle').click(function () {
                        location.href = '/api/article';
                    });
                    $('#btnGoApiCategory').click(function () {
                        location.href = '/api/categories';
                    });
                    $('#btnGo').click(function () {
                        $('#url-page').text($('[name="urlPage"]').val());
                        $('#link-selector').text($('[name="linkSelector"]').val());
                        $('#title-selector').text($('[name="titleSelector"]').val());
                        $('#img-selector').text($('[name="imgSelector"]').val());
                        $('#des-selector').text($('[name="desSelector"]').val());
                        $('#content-selector').text($('[name="contentSelector"]').val());
                        $('#myModal').modal();
                    });

                    $('#btnSave').click(function () {
                        var dataToSend = {
                            url: $('[name="urlPage"]').val(),
                            titleSelector: $('[name="titleSelector"]').val(),
                            desSelector: $('[name="desSelector"]').val(),
                            imgSelector: $('[name="imgSelector"]').val(),
                            contentSelector: $('[name="contentSelector"]').val(),
                            linkSelector: $('[name="linkSelector"]').val(),
                        };
                        console.log(dataToSend);
                        $.ajax({
                            url: "/admin/crawler-source",
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
