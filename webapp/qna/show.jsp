<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html lang="ko">
<%@ include file="/include/header.jspf"%>
<body>
<%@ include file="/include/navigation.jspf" %>

<div class="container" id="main">
    <header class="qna-header">
        <h2 class="qna-title">${question.title}</h2>
    </header>
    <div class="content-main">
        <article class="article">
            <div class="article-header">
                <div class="article-header-thumb">
                    <img src="/img/picture.jpeg" class="article-author-thumb" alt="">
                </div>
                <div class="article-header-text">
                    <span class="article-author-name">${question.writer}</span>
                    <span class="article-header-time">
                        <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${question.createdDate}"/>
                    </span>
                </div>
            </div>
            <div class="article-doc">
                <p>${question.contents}</p>
            </div>
            <div class="article-util">
                <ul class="article-util-list">
                    <%-- 로그인한 사용자가 작성자인 경우에만 수정/삭제 버튼 표시 --%>
                    <c:if test="${not empty sessionScope.user}">
                        <c:if test="${sessionScope.user.userId eq question.writer}">
                            <li>
                                <a class="link-modify-article" href="/qna/updateForm?questionId=${question.questionId}">수정</a>
                            </li>
                            <li>
                                <a class="link-delete-article" href="/qna/delete?questionId=${question.questionId}">삭제</a>
                            </li>
                        </c:if>
                    </c:if>
                    <li>
                        <a class="link-modify-article" href="/">목록</a>
                    </li>
                </ul>
            </div>
        </article>

        <div class="qna-comment">
            <div class="qna-comment-kuit">
                <p class="qna-comment-count"><strong>${question.countOfAnswer}</strong>개의 의견</p>
                <div>
                    <%-- 답변 목록은 나중에 구현 (4단계에서) --%>
                    <%-- 지금은 답변 기능 없이 질문만 표시 --%>

                    <%-- 답변 작성 폼도 나중에 구현 --%>
                    <c:if test="${not empty sessionScope.user}">
                        <form class="submit-write">
                            <div class="form-group" style="padding:14px;">
                                <textarea class="form-control" placeholder="답변을 입력하세요" disabled></textarea>
                            </div>
                            <button class="btn btn-primary pull-right" type="button" disabled>답변하기 (준비중)</button>
                            <div class="clearfix" />
                        </form>
                    </c:if>

                    <c:if test="${empty sessionScope.user}">
                        <p style="text-align: center; padding: 20px;">
                            답변을 작성하려면 <a href="/user/login.jsp">로그인</a>이 필요합니다.
                        </p>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="/js/scripts.js"></script>
</body>
</html>