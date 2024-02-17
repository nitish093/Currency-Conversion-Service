package com.in28minutes.microservices.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    private CurrencyExchangeProxy currencyExchangeProxy;

    @Autowired
    public CurrencyConversionController(CurrencyExchangeProxy currencyExchangeProxy) {
        this.currencyExchangeProxy = currencyExchangeProxy;
    }

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,
                                                          @PathVariable String to,
                                                          @PathVariable BigDecimal quantity)
    {
        //URI: http://localhost:8001/currency-exchange/from/EUR/to/INR
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("from",from);
        hashMap.put("to",to);
        ResponseEntity<CurrencyConversion> currencyConversionResponse = new RestTemplate().getForEntity("http://localhost:8001/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class,
                hashMap);

        CurrencyConversion tempCurrencyConversion = currencyConversionResponse.getBody();
        System.out.println("tempCurrencyConversion:"+tempCurrencyConversion);
        return new CurrencyConversion(10001L,
                from,
                to,
                quantity,
                tempCurrencyConversion.getConversionMultiple(),
                quantity.multiply(tempCurrencyConversion.getConversionMultiple()),
                tempCurrencyConversion.getEnvironment());
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from,
                                                               @PathVariable String to,
                                                               @PathVariable BigDecimal quantity)
    {

        CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from,to);
        System.out.println("currencyConversion:"+currencyConversion);

        return new CurrencyConversion(10001L,
                from,
                to,
                quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment());
    }

}
