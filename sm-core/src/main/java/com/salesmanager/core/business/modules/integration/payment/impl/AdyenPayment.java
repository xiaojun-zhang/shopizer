package com.salesmanager.core.business.modules.integration.payment.impl;

import com.adyen.Client;
import com.adyen.Util.Util;
import com.adyen.constants.ApiConstants;
import com.adyen.enums.Environment;
import com.adyen.model.Amount;
import com.adyen.model.checkout.PaymentsRequest;
import com.adyen.model.checkout.PaymentsResponse;
import com.adyen.service.Checkout;
import com.adyen.service.exception.ApiException;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.payments.Transaction;
import com.salesmanager.core.model.payments.TransactionType;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import com.salesmanager.core.model.system.IntegrationConfiguration;
import com.salesmanager.core.model.system.IntegrationModule;
import com.salesmanager.core.modules.integration.IntegrationException;
import com.salesmanager.core.modules.integration.payment.model.PaymentModule;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AdyenPayment implements PaymentModule {
    @Override
    public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store) throws IntegrationException {

    }

    @Override
    public Transaction initTransaction(MerchantStore store, Customer customer, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }

    @Override
    public Transaction authorize(MerchantStore store, Customer customer, List<ShoppingCartItem> items, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }

    @Override
    public Transaction capture(MerchantStore store, Customer customer, Order order, Transaction capturableTransaction, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }

    @Override
    public Transaction authorizeAndCapture(MerchantStore store, Customer customer, List<ShoppingCartItem> items,
                                           BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        String merchantAccount = configuration.getIntegrationKeys().get("merchant_account");;
        String apiKey = configuration.getIntegrationKeys().get("api_key");

        Environment environment= Environment.LIVE;
        if (configuration.getEnvironment().equals("TEST")) {// sandbox
            environment= Environment.TEST;
        }

        // Set your X-API-KEY with the API key from the Customer Area.
        Client client = new Client(apiKey, environment);
        Checkout checkout = new Checkout(client);
        PaymentsRequest paymentsRequest = new PaymentsRequest();
        paymentsRequest.setMerchantAccount(merchantAccount);
        paymentsRequest.setChannel(PaymentsRequest.ChannelEnum.WEB);

        Amount adyenAmount = Util.createAmount(amount, store.getCurrency().getCode());
        paymentsRequest.setAmount(adyenAmount);

        String encryptedCardNumber = "adyenjs_0_1_25$RFIEQ7WFWX7V/KxU9ARGvxyxnEjbDqJzv4ovGnIEIlfaS6jBrniCGHVlOU+MWYg2x8AhmWb1TfnZUvNUNQQLViXzbWJNQI2m0+DAM7+A+nTCkOllqTTjqYfChzxmBnEaRN45GoX199nHX/95/de9i3VFRPsCBCzMcC/rIJ2+cusOS04autSevrE0+HDeRViziNvinDYginKk0pXxiMOLwINE+q1cd/mAEfjnHMWiYAiO29SPC0dCHIvXpMs8s85+dB9SbuxPWgrIm9y3zbgIE9lpHNWR7yXQPMrDaJoEwk//oUjKQGzNElrJMAirWZjm+gFrVyhO8KxjqYViLxULCg==$83+/ZHqUNtPsQo5sW2NB5fZsdtR/Xf6ECDENNznXwp32HHDi8L/qe0rHXHa49Skv4JlvY61D96ydYKMUMdBN0RzItpQUodMeBVpxudGC2iHZmNmea/orAFiUBxsLw3r0SM1cvXW6JxZIvccd10pg9IH98jvzn9rIP4ZtvwNqrFAjVv730AvMvS6icj1GdVh9bT8oQ/Q1sDD/8ahOfbD9rE90uiUi2nwMil9rF2P4NFqqaAmktzNgBfmUeToP/RLmUuUO1VXz4wv2gAbQaZZsNIUW4QHhAcNYqydURa86hjhY57xjBDFaVTnYf88V4Ori4SaympVtCIJ91Ft8uBklePnerDGxq+F0FzI9oeSWuHrGRMLPHOLH9DZS0A92AXLy2Cj0ZCjSZl79vQn5uiMvkL4juuY6D0ExE3aWCZpDez7cfS4lkjkzph2hTlzVz7dk7G5gYYacfRXFdcootLhTCChIAmaEquhrscPunHDnxNtpaBJgSSoQ3V1ELCYoSj8tC1Xgjs5Lcz505XESbVQnAZwCYO/R0yWk2SL2EUutwrGdXxUrg0Cv2hrU+KzaoB0haJWrOLskLg3uq96KeDQkfFdLcAsSyAyrQ8fqVTLXhDC9L1dIjc6q7DIb2L/CnSlL6/yKdLQfsbG+VVpDpjFhT/eLOYKkLjLrfwgZtadO3lh9Ml51ooK+3RIG0WSe05ojeF1p7q3kiJgC0w6f6natugFYP7Is/ohcyV9yi0sRyN1xyHK0VZqPUlMu";
        String encryptedExpiryMonth = "adyenjs_0_1_25$fMRBNzjjuqSQl9FVrhNA4fSPygDuXiXM3zD69nuctO28cN5DBzWlOPwZPZuuRZN7aIdW8SHsdX82RI5Ei+v4svd+9P8/uXo67JqF6+J0nOTnXCONn+3/hA1u/BptnkCtV4JJvb/tFHl2TiH97+4ikwIekoK8bEpcJvo0Szdfyxt7Nkt4/SzJH3OHZfZ4kiwqVkKMslqXUpUCtPu61WxsX1vCYPuGiNFHjEYksURpDQhD7frFLvr0fYh720mjkIrsD6gfrrokAwIsyuxtuiPtkyMNKX80YVupV0xxSCVW0SEhWDLBUlzVn86K0r7zEDp/W0N0gSoKuDa+aBKI5Uepwg==$ZpUYGANo/EwJx0gvCvbdR2S/x7lO9BPbkCjME9LpkIp/UokqhbEW0n8Sn8ZjzSkOWQUdSet4DeYxEfAlYc8mZ2BEWIdfqRNHZpg8fD+myX6E+hyTqtgHEslT/i4zSfr9Sms9dEm9klObrWgM1sSPIYbNTcC3gNnbfytO7COK1ppPjr4eTck25y6mMz/VeEMQHRYAU8QbGQIgOAQkxiq51xFitdmqJyRnj04SiUiybgcnGU9oxvSkASTo98xs5RATtUDGkK65sNUSTGZPggB1HIGsJcudCjkX9jVGlYKpeKUcjb+OAaChVExPY3Cwi61hQAFGpW4XwUogGC7/ak2IGloVWz4BEDezI/LtslHu/0tpvks2D2jsSHFaYkTmlMrhI2boe0XD1/V9tB+dpWL1kVuxXteorU0/8Zy3Ac+HpiIPdscgDejCrsT09D0I68SQchnsyxM3KZHR2yu0IYM8j1Fh59I=";
        String encryptedExpiryYear = "adyenjs_0_1_25$qzhxw7b70ppLckJNRXDOcSrHpVLuBSECi3t3gAK4moXVLI+WgwwT0Z90lVI+pxyVGPTlCrKmIlHEHGELmGaxr/A2tmg/FUqHzTPOrLMKm0NQh9dpXsGunMw/xC0OJwME88iZAh5DnwPFtjS5/E8jNUZkjt6hOgaoWDUfolMmP83Tpd51bhQT+QjTOhgTslg00q/qVM1NeFBw/Kzf3ibvzE1R0bZsBVU+IL01Pwo0NcPO1WCRLyvjHRU5Ea1Z3r70+CDH9SuQD6Uq/m9MYjSIvrkDUlGguI4wAg/A4MCbFsEnuDkU8pTiM9S5Rp3XcUl2FA3e+Xf27J0WtOAkHys7jw==$YVv41bo1KrtOiSTZcg/aZcXTPG0yPgKtJOM9AXji8mkv0X9pPzRmoJ8UzocWfQQU00u+9mRGn9ZzoBwpg/whhU7UmbT0rc0QmHhFrHByvNrUsXNspMJmqcXX30M27g9kLpamom+OxrrHAZP3GezRU3vCYvPdpzrprm/DF8x3PUsZhG3rArBAqit9Yo993mtau/P1zQEyXKZorpS4Bt6YX1nkE/mUTw8I5mI4Y4Wer/CSpgnYP6xGXKFmsXksen3ogH0uBwp1e4x8FWjxeQSo/uX9iuFsbBt5OQRePDwqZU1WXjJDkodLtjYBdgMR8vf2jRkzIrTa2by+Hud3IsVIQkuRlx4LSV+lN4YDzI97sK7HF0At6QX2bm3PV/4vRwpOEBXZt2B54qbu9OF7nWxKm1UeQOM+2L9doGPp7hFwiigbl9yqxdkqNPwfBo3O8OnBIR/NNZoCMZrePi+Gq/wBvmE60Rot";
        String encryptedSecurityCode = "adyenjs_0_1_25$ZeE9B/eTDiJuf91LAojEmrh+vSoCRWc3GUTz8axX6OG92UpWVP+obR2NBtNKXGozM8BMECj8rNc7iEuPTV/Jz82oe/tPcLubd5B6423paxdCv3pTSeraJPbc6B3T3y1eicYeoQl2DIoYSCc1cr0yokt1E1qkE2waWYWL2J9EKzIE3FXsWbU0ZisYQ5CwWgZ5CQe0Nkv1U7YqJiGDcxyMbiV4WXcrPfBvuXW8JRS1cAilofh8Qj7B0yShrDDNUXASdmB0cwzI2zGOxgSFaJcgUzXr3LFJZskW+6BNyyC5K4V5TuQt1pQJviwNjX8eFquGbW/4T7EC2jNIPDzxD7+PVg==$NUd0TXkN84dZCVf3i5apE8Jsqxi4AWDWUiOR+rFFkSw6EZdH/CFikN3UXyagOJv0r6alXZ8iPyGTnI1wEVRWpSTaFOX9LuGaFQkYEAJ14/N3YMiaoLBBGx1ZBbMdEY55bWAvWK2NHqo+JyOY5wtoRLAWNXg0tuTWX5KEJ+LT7XCDcwpPnNq3dB2XHoSCDIz+cDK/SBsFYEmedqrlkl6brd+oWsqsMOcsqa2pvM8f4YiHBkDoe1c5Ow2cqcIKB5INdty/q3PNmTOLmbdYSPiB/fdajdRClUSlhhzf3h4FtJ5wuMKfOEWAAnn0unQVTs/mD7NZgBoPBzY492iutB0P7Bf3rDOKrGinV6bLwNFifepfK+axX6JMtc2kvDCi3gNI0qmWbpjzZBcOIuLf7BKSKvBS/DGIyNlv97tF+5CqojzhgWuunc0L3rq6kb/wI4Q+yQd/GomZ2SciXx1eRA==";
//        String encryptedCardNumber = payment.getPaymentMetaData().get("creditcard_card_number");
//        String encryptedExpiryMonth = payment.getPaymentMetaData().get("creditcard_card_expirationmonth");
//        String encryptedExpiryYear = payment.getPaymentMetaData().get("creditcard_card_expirationyear");
//        String encryptedSecurityCode = payment.getPaymentMetaData().get("creditcard_card_cvv");

        paymentsRequest.setReference(UUID.randomUUID().toString());
        paymentsRequest.addEncryptedCardData(encryptedCardNumber,encryptedExpiryMonth, encryptedExpiryYear, encryptedSecurityCode, "John Smith");
        paymentsRequest.setReturnUrl("http://localhost:8080");
        paymentsRequest.getPaymentMethod().setType("scheme");

        PaymentsResponse paymentsResponse = null;
        try {
            paymentsResponse = checkout.payments(paymentsRequest);
        } catch (Exception e) {
            IntegrationException te = new IntegrationException(
                    "Can't process Adyen auth + capture " + e.getMessage());
            te.setExceptionType(IntegrationException.TRANSACTION_EXCEPTION);
            te.setMessageCode("message.payment.error");
            te.setErrorCode(IntegrationException.TRANSACTION_EXCEPTION);
            throw te;
        }

        String trxId = null;
        if (!paymentsResponse.getResultCode().equals(PaymentsResponse.ResultCodeEnum.REFUSED) && !paymentsResponse.getResultCode().equals(PaymentsResponse.ResultCodeEnum.ERROR)) {
            trxId  = paymentsResponse.getPspReference();
        } else {
            String errorString = paymentsResponse.getRefusalReason();

            IntegrationException te = new IntegrationException(
                    "Can't process Adyen auth + capture " + errorString);
            te.setExceptionType(IntegrationException.TRANSACTION_EXCEPTION);
            te.setMessageCode("message.payment.error");
            te.setErrorCode(IntegrationException.TRANSACTION_EXCEPTION);
            throw te;

        }

        if(StringUtils.isBlank(trxId)) {
            IntegrationException te = new IntegrationException(
                    "Can't process Adyen, missing trxId");
            te.setExceptionType(IntegrationException.TRANSACTION_EXCEPTION);
            te.setMessageCode("message.payment.error");
            te.setErrorCode(IntegrationException.TRANSACTION_EXCEPTION);
            throw te;
        }

        Transaction trx = new Transaction();
        trx.setAmount(amount);
        trx.setTransactionDate(new Date());
        trx.setTransactionType(TransactionType.AUTHORIZECAPTURE);
        trx.setPaymentType(PaymentType.CREDITCARD);
        trx.getTransactionDetails().put("TRANSACTIONID", trxId);
        trx.getTransactionDetails().put("TRNAPPROVED", null);
        trx.getTransactionDetails().put("TRNORDERNUMBER", trxId);
        trx.getTransactionDetails().put("MESSAGETEXT", null);

        return trx;
    }

    @Override
    public Transaction refund(boolean partial, MerchantStore store, Transaction transaction, Order order, BigDecimal amount, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }
}
