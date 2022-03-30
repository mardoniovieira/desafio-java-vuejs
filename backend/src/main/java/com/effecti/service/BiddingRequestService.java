package com.effecti.service;

import com.effecti.exception.BiddingRequestException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BiddingRequestService {

    // It may be necessary to download the ssl certificate from the Sefaz website and add it to Java cacerts:
    // 		keytool -import -trustcacerts -alias sefaz -keystore "C:\Program Files\Java\jdk-11.0.12\lib\security\cacerts" -file sefaz.cer

    @Autowired
    private HttpService httpService;

    @Value("${url}")
    private String url;
    @Value("${host}")
    private String host;

    private static String JSESSIONID = null;

    public List<Map<String, String>> getBiddingsMapByPage(Integer page) throws IOException {
        if (JSESSIONID == null) {
            updateJsessionid();
        }
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("AJAXREQUEST", "formularioDeCrud:j_id367"));
        parameters.add(new BasicNameValuePair("formularioDeCrud:datascrollerInferior", String.valueOf(page)));
        parameters.add(new BasicNameValuePair("javax.faces.ViewState", "j_id1"));
        HttpResponse response = httpService.executePostWithFormData(url, JSESSIONID, host, parameters);
        String html = EntityUtils.toString(response.getEntity()).replace("\n", "");
         if (!pageNumberIsValid(html, page)) {
            return new ArrayList<>();
        }
        return fromHtmlToMap(html);
    }

    public void updateJsessionid() throws IOException {
        HttpResponse response = httpService.executeGet(url);
        String jsessionid = response.getFirstHeader("Set-Cookie").getValue();
        JSESSIONID = jsessionid.substring(0, jsessionid.indexOf(";"));
    }

    private Boolean pageNumberIsValid(String html, Integer page) {
        Pattern patternPages = Pattern.compile("(?<=<span id=\"formularioDeCrud:outputNumeracaoInferior\">)(.*?)(?=</span>)");
        Matcher matcherPages = patternPages.matcher(html);
        if(matcherPages.find()){
            String pagesHtml = matcherPages.group(); // example: 1 a 10 de 231140
            List<String> p = Arrays.asList(pagesHtml.split(" "));
            int totalRecords = Integer.parseInt(p.get(p.size()-1)); // example: 231140
            return page > 0 && page <= Math.ceil(totalRecords / 10.0);
        }
        return false;
    }

    private List<Map<String, String>> fromHtmlToMap(String html) {
        List<String> headers = new ArrayList<>();
        List<Map<String, String>> biddings = new ArrayList<>();

        Pattern patternTable = Pattern.compile("(?<=<table class=\"rich-table\" id=\"formularioDeCrud:pagedDataTable\")(.*?)(?=</table>)");
        Matcher matcherTable = patternTable.matcher(html);
        if (!matcherTable.find()) {
            throw new BiddingRequestException("Match table failed.");
        }
        String table = matcherTable.group();

        Pattern patternHead = Pattern.compile("(?<=<thead)(.*)(?=</thead>)");
        Matcher matcherHead = patternHead.matcher(table);
        if (!matcherHead.find()) {
            throw new BiddingRequestException("Match table head failed.");
        }
        String headHtml = matcherHead.group();

        Pattern patternHeadValue = Pattern.compile("(?<=<span class=\"coluna\">)(.*?)(?=</span>)");
        Matcher matcherHeadValue = patternHeadValue.matcher(headHtml);
        matcherHeadValue.results().forEach(matchResult -> {
            String header = matchResult.group();
            headers.add(header);
        });

        Pattern patternBody = Pattern.compile("(?<=<tbody)(.*)(?=</tbody>)");
        Matcher matcherBody = patternBody.matcher(table);
        if (!matcherBody.find()) {
            throw new BiddingRequestException("Match table body failed.");
        }
        String bodyHtml = matcherBody.group();

        Pattern patternBodyTr = Pattern.compile("(?<=<tr)(.*?)(?=</tr>)");
        Matcher matcherBodyTr = patternBodyTr.matcher(bodyHtml);
        matcherBodyTr.results().forEach(matchResult -> {
            Map<String, String> bidding = new HashMap<>();
            String bodyTr = matchResult.group();
            Pattern patternBodyValue = Pattern.compile("(?<=<td)(.*?)(?=</td>)");
            Matcher matcherBodyValue = patternBodyValue.matcher(bodyTr);

            int index = 0; // this counter is used to link the header with the column
            Iterator<MatchResult> bodyValueIterator = matcherBodyValue.results().iterator();
            while(bodyValueIterator.hasNext()) {
                String td = bodyValueIterator.next().group();
                if (td.contains("panelDeCheckSingle\">")) {
                    continue; // Skip the select box component
                }
                if (td.contains("</span>")) { // Multivalued columns have the span component
                    Pattern patternBodySpan = Pattern.compile("(?<=><span)(.*?)(?=</span>)");
                    Matcher matcherBodySpan = patternBodySpan.matcher(td);
                    if (!matcherBodySpan.find()) {
                        throw new BiddingRequestException("Match table column failed.");
                    }
                    String bodySpan = matcherBodySpan.group();
                    bidding.put(headers.get(index), bodySpan.substring(bodySpan.indexOf(">")+1));
                } else {
                    bidding.put(headers.get(index), td.substring(td.indexOf(">")+1));
                }
                index++;
            }

            biddings.add(bidding);
        });

        return biddings;
    }
}
