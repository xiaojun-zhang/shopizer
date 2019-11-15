<%
response.setCharacterEncoding("UTF-8");
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/shopizer-tags.tld" prefix="sm" %>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<style>
/* Uses Bootstrap stylesheets for styling, see linked CSS*/
body {
  background-color: #fff;
}

.panel {
  width: 80%;
  margin: 2em auto;
}

.bootstrap-basic {
  background: white;
}

.panel-body {
  width: 90%;
  margin: 2em auto;
}

.helper-text {
  color: #8A6D3B;
  font-size: 12px;
  margin-top: 5px;
  height: 12px;
  display: block;
}

@media (max-width: 670px) {
  .btn {
    white-space: normal;
  }
}
</style>

<script type="text/javascript">

function populateData(div, data, defaultValue) {
	$.each(data, function() {
	    div.append($('<option/>').val(this).text(this));
	});
    if(defaultValue && (defaultValue!=null && defaultValue!='')) {
    	div.val(defaultValue);
    }
}

</script>

<script src="https://checkoutshopper-test.adyen.com/checkoutshopper/sdk/3.3.0/adyen.js"></script>
<link rel="stylesheet" href="https://checkoutshopper-test.adyen.com/checkoutshopper/sdk/3.3.0/adyen.css"/>

<div id="card-container" class="payment-method__container"> </div>

<script type="text/javascript">
       function handleOnChange(state, component) {

       }

       function handleOnAdditionalDetails(state, component) {

       }

       const configuration = {
           locale: "en_US",
           environment: "test",
           originKey: "pub.v2.8015732778352430.aHR0cDovL2xvY2FsaG9zdDo4MDgw.MsakjatsScJCnHifvExAM2M77HGMA0r_i7j_4bgxz98",
           onChange: handleOnChange,
           onAdditionalDetails: handleOnAdditionalDetails
       };

       var checkout = new AdyenCheckout(configuration);

           const card = checkout
               .create('card', {
                   styles: {},

                   placeholders: {
                   },

                   showPayButton: false,

                   onSubmit: (state, component) => {
                       if (state.isValid) {
                            console.log("onSubmit");
                       }
                   },

                   onChange: (state, component) => {
                       if (state.isValid) {
                            console.log(state.data);

                            var $form = $('#checkoutForm');
                            var cardNumberField = '<input type="hidden" name="payment[\'creditcard_card_number\']" value="' + state.data.paymentMethod.encryptedCardNumber +'" />';
                            var cardCvvField = '<input type="hidden" name="payment[\'creditcard_card_cvv\']" value="' + state.data.paymentMethod.encryptedSecurityCode +'" />';
                            var cardExpirationMonthField = '<input type="hidden" name="payment[\'creditcard_card_expirationmonth\']" value="' + state.data.paymentMethod.encryptedExpiryMonth +'" />';
                            var cardExpirationYearField = '<input type="hidden" name="payment[\'creditcard_card_expirationyear\']" value="' + state.data.paymentMethod.encryptedExpiryYear +'" />';
                            $form.append(cardNumberField);
                            $form.append(cardCvvField);
                            $form.append(cardExpirationMonthField);
                            $form.append(cardExpirationYearField);


                       }
                   }
               })
       .mount('#card-container');
</script>

