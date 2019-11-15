<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>





                 <div class="control-group">
                        <label class="required"><s:message code="module.payment.adyen.merchantAccount" text="Merchant Account"/></label>
	                        <div class="controls">
	                        		<form:input cssClass="input-xxlarge highlight" path="integrationKeys['merchant_account']" />
	                        </div>
	                        <span class="help-inline">
	                        	<c:if test="${merchant_account!=null}">
	                        	<span id="identifiererrors" class="error"><s:message code="module.payment.adyen.message.merchant_account" text="Field in error"/></span>
	                        	</c:if>
	                        </span>
                  </div>

                   <div class="control-group">
                        <label class="required"><s:message code="module.payment.adyen.apiKey" text="API key"/></label>
	                        <div class="controls">
									<form:input cssClass="input-xxlarge highlight" path="integrationKeys['api_key']" />
	                        </div>
	                        <span class="help-inline">
	                        	<c:if test="${api_key!=null}">
	                        		<span id="apikeyerrors" class="error"><s:message code="module.payment.adyen.message.api_key" text="Field in error"/></span>
	                        	</c:if>
	                        </span>
                  </div>

                    <div class="control-group">
                        <label class="required"><s:message code="module.payment.adyen.originKey" text="Origin key"/></label>
	                        <div class="controls">
									<form:input cssClass="input-xxlarge highlight" path="integrationKeys['origin_key']" />
	                        </div>
	                        <span class="help-inline">
	                        	<c:if test="${origin_key!=null}">
	                        		<span id="originkeyerrors" class="error"><s:message code="module.payment.adyen.message.origin_key" text="Field in error"/></span>
	                        	</c:if>
	                        </span>
                  </div>

                   <div class="control-group">
                        <label class="required"><s:message code="module.payment.transactiontype" text="Transaction type"/></label>
	                        <div class="controls">
	                        		<form:radiobutton cssClass="input-large highlight" path="integrationKeys['transaction']" value="AUTHORIZE" />&nbsp;<s:message code="module.payment.transactiontype.preauth" text="Pre-authorization" /><br/>
	                        		<form:radiobutton cssClass="input-large highlight" path="integrationKeys['transaction']" value="AUTHORIZECAPTURE" />&nbsp;<s:message code="module.payment.transactiontype.sale" text="Sale" /></br>
	                        </div>
                  </div>


