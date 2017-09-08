package com.yee.study.java.json.jsonpath;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.yee.study.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * JsonPath 示例
 * @author Roger.Yi
 */
public class JsonPathSample
{
    private static final Logger logger = LoggerFactory.getLogger(JsonPathSample.class);

    public static void main(String[] args)
    {
        String json = "{\n" +
                "    \"applyId\" : \"296180\",\n" +
                "    \"loanApplyInfo\" : {\n" +
                "        \"data\" : {\n" +
                "            \"loanApplyId\" : 296180,\n" +
                "            \"partyId\" : \"1012222100196946\",\n" +
                "            \"username\" : \"13867583365\",\n" +
                "            \"personName\" : \"冯余军\",\n" +
                "            \"certNo\" : \"330681198211228392\",\n" +
                "            \"specLongitude\" : 120.249178,\n" +
                "            \"specLatitude\" : 29.710811,\n" +
                "            \"specCoordType\" : \"1\",\n" +
                "            \"specLocation\" : \"浙江省绍兴市诸暨市中信银行(绍兴诸暨支行)\"\n" +
                "        },\n" +
                "        \"dsDataId\" : null\n" +
                "    },\n" +
                "    \"pcrReport\" : {\n" +
                "        \"data\" : {\n" +
                "            \"baseInfo\" : {\n" +
                "                \"id\" : 217801,\n" +
                "                \"creditNo\" : \"2017031000003718348915\",\n" +
                "                \"reportTime\" : \"20170310103453\",\n" +
                "                \"personName\" : \"冯余军\",\n" +
                "                \"loginName\" : \"13867583365\",\n" +
                "                \"partyId\" : \"1012222100196946\",\n" +
                "                \"userName\" : \"13867583365\",\n" +
                "                \"cerType\" : \"身份证\",\n" +
                "                \"shortCerNo\" : \"8392\",\n" +
                "                \"maritalStatus\" : \"已婚\",\n" +
                "                \"creditCardNum\" : 16,\n" +
                "                \"overduedCounts\" : 1,\n" +
                "                \"exceedNinetyDaysCounts\" : 0,\n" +
                "                \"maxMonthsOverdue\" : 1,\n" +
                "                \"haveBadDebts\" : false,\n" +
                "                \"totalCreditLine\" : 313417,\n" +
                "                \"totalCreditLineUsed\" : 83491,\n" +
                "                \"loanFreq\" : 2,\n" +
                "                \"totalLoanAmount\" : 31000,\n" +
                "                \"totalLoanBalance\" : 19448,\n" +
                "                \"createTime\" : \"20170310131333\",\n" +
                "                \"cceRate\" : \"优秀\",\n" +
                "                \"pcrDataId\" : 234983,\n" +
                "                \"serverCheckResult\" : \"0\"\n" +
                "            },\n" +
                "            \"creditCardStats\" : {\n" +
                "                \"cardCount\" : 16,\n" +
                "                \"badDebtCardCount\" : 0,\n" +
                "                \"overDue90DayCardCount\" : 0,\n" +
                "                \"overDueUpToNowCardCount\" : 0,\n" +
                "                \"totalMonthsOverdue\" : 1,\n" +
                "                \"validCNYCreditCardCount\" : 13,\n" +
                "                \"validCNYCreditCardMaxCreditLine\" : 83620,\n" +
                "                \"validCNYCreditCardTotalCreditLine\" : 282417,\n" +
                "                \"validCNYCreditCardTotalCreditLineUsed\" : 83491,\n" +
                "                \"validCNYCreditCardCreditUsageRate\" : 0.2956,\n" +
                "                \"validCNYCreditCardMinOpenDate\" : \"20031215000000\",\n" +
                "                \"validCNYCreditCardMaxOpenMonthSpan\" : 165\n" +
                "            },\n" +
                "            \"loanStats\" : {\n" +
                "                \"loanCount\" : 2,\n" +
                "                \"badDebtLoanCount\" : 0,\n" +
                "                \"overDue90DayLoanCount\" : 0,\n" +
                "                \"overDueUpToNowLoanCount\" : 0,\n" +
                "                \"totalMonthsOverdue\" : 0\n" +
                "            },\n" +
                "            \"accessStats\" : {\n" +
                "                \"accessCount\" : 50,\n" +
                "                \"selfInqInLast3m\" : 2,\n" +
                "                \"auditInqInLast3m\" : 2,\n" +
                "                \"selfInqInLast6m\" : 4,\n" +
                "                \"selfInqViaCounterInLast6m\" : 0,\n" +
                "                \"counterQueryCount\" : 0\n" +
                "            },\n" +
                "            \"pcrLoanRecords\" : [ \n" +
                "                {\n" +
                "                    \"id\" : 996474,\n" +
                "                    \"loanDate\" : \"20161019000000\",\n" +
                "                    \"loanAmount\" : 10000,\n" +
                "                    \"currencyCode\" : \"CNY\",\n" +
                "                    \"loanType\" : \"9\",\n" +
                "                    \"loanStatus\" : \"02\",\n" +
                "                    \"reportDate\" : \"20170201000000\",\n" +
                "                    \"loanBalance\" : 6848,\n" +
                "                    \"monthsOverdue\" : 0,\n" +
                "                    \"exceedNinetyDaysMonths\" : 0,\n" +
                "                    \"issuerName\" : \"招联消费金融有限公司\",\n" +
                "                    \"creditId\" : 217801,\n" +
                "                    \"recordDetails\" : \"2016年10月19日招联消费金融有限公司发放的10,000元（人民币）个人消费贷款，2017年10月19日到期。截至2017年2月，余额6,848。\",\n" +
                "                    \"accountType\" : \"B\"\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"id\" : 996475,\n" +
                "                    \"loanDate\" : \"20160619000000\",\n" +
                "                    \"loanAmount\" : 21000,\n" +
                "                    \"currencyCode\" : \"CNY\",\n" +
                "                    \"loanType\" : \"4\",\n" +
                "                    \"loanStatus\" : \"02\",\n" +
                "                    \"reportDate\" : \"20170101000000\",\n" +
                "                    \"loanBalance\" : 12600,\n" +
                "                    \"monthsOverdue\" : 0,\n" +
                "                    \"exceedNinetyDaysMonths\" : 0,\n" +
                "                    \"issuerName\" : \"深圳前海微众银行股份有限公司\",\n" +
                "                    \"creditId\" : 217801,\n" +
                "                    \"recordDetails\" : \"2016年6月19日深圳前海微众银行股份有限公司发放的21,000元（人民币）个人消费贷款，2018年2月19日到期。截至2017年1月，余额12,600。\",\n" +
                "                    \"accountType\" : \"B\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"pcrAccessRecords\" : [ \n" +
                "                {\n" +
                "                    \"id\" : 6984925,\n" +
                "                    \"accessDate\" : \"20170305000000\",\n" +
                "                    \"operator\" : \"本人\",\n" +
                "                    \"accessReason\" : \"本人查询（互联网个人信用信息服务平台）\",\n" +
                "                    \"reasonTypes\" : \"04\",\n" +
                "                    \"creditId\" : 217801\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"id\" : 6984974,\n" +
                "                    \"accessDate\" : \"20150319000000\",\n" +
                "                    \"operator\" : \"广发银行/*hkbmq0*0\",\n" +
                "                    \"accessReason\" : \"贷后管理\",\n" +
                "                    \"reasonTypes\" : \"03\",\n" +
                "                    \"creditId\" : 217801\n" +
                "                }\n" +
                "            ],\n" +
                "            \"pcrCardCreditRecords\" : [ \n" +
                "                {\n" +
                "                    \"id\" : 2280516,\n" +
                "                    \"issuerName\" : \"交通银行\",\n" +
                "                    \"openDate\" : \"20101213000000\",\n" +
                "                    \"cardType\" : \"1\",\n" +
                "                    \"currencyCode\" : \"CNY\",\n" +
                "                    \"reportDate\" : \"20170201000000\",\n" +
                "                    \"creditCardStatus\" : \"07\",\n" +
                "                    \"creditLine\" : 13000,\n" +
                "                    \"creditLineUsed\" : 6044,\n" +
                "                    \"monthsOverdue\" : 1,\n" +
                "                    \"exceedNinetyDaysMonths\" : 0,\n" +
                "                    \"creditId\" : 217801,\n" +
                "                    \"recordDetails\" : \"2010年12月13日交通银行发放的贷记卡（人民币账户）。截至2017年2月，信用额度13,000，已使用额度6,044。最近5年内有1个月处于逾期状态，没有发生过90天以上逾期。\",\n" +
                "                    \"accountType\" : \"A\"\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"id\" : 2280531,\n" +
                "                    \"issuerName\" : \"中国建设银行浙江省分行\",\n" +
                "                    \"openDate\" : \"20151220000000\",\n" +
                "                    \"cardType\" : \"1\",\n" +
                "                    \"currencyCode\" : \"CNY\",\n" +
                "                    \"reportDate\" : \"20151201000000\",\n" +
                "                    \"creditCardStatus\" : \"04\",\n" +
                "                    \"creditLine\" : 31000,\n" +
                "                    \"monthsOverdue\" : 0,\n" +
                "                    \"exceedNinetyDaysMonths\" : 0,\n" +
                "                    \"creditId\" : 217801,\n" +
                "                    \"recordDetails\" : \"2015年12月20日中国建设银行浙江省分行发放的贷记卡（人民币账户）。截至2015年12月，信用额度31,000，尚未激活。\",\n" +
                "                    \"accountType\" : \"B\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        \"dsDataId\" : \"217801\"\n" +
                "    },\n" +
                "    \"zmxyReport\" : {\n" +
                "        \"time\" : ISODate(\"2017-09-05T11:12:55.475+08:00\"),\n" +
                "        \"data\" : {\n" +
                "            \"mobileNo\" : \"13867583365\",\n" +
                "            \"success\" : true,\n" +
                "            \"authorized\" : true,\n" +
                "            \"available\" : true,\n" +
                "            \"openId\" : \"268806157569931298842426450\",\n" +
                "            \"authTime\" : \"20170605173233\",\n" +
                "            \"bizNo\" : \"ZM201709053000000764500711330831\",\n" +
                "            \"time\" : \"20170905111255\",\n" +
                "            \"zmScore\" : \"682\"\n" +
                "        },\n" +
                "        \"dsDataId\" : \"59ae163742bb2c74417a6d4b\"\n" +
                "    }\n" +
                "}";

        DocumentContext context = JsonPath.parse(json);

        // 获取 applyId
        logger.info("获取applyId : {}", context.read("$.applyId", String.class));

        // 获取 贷款记录数 (类型为4和9的记录)
        List<Object> loanRecords = context.read("$.pcrReport.data.pcrLoanRecords[?(@.loanType == '4' || @.loanType == '9')]");
        logger.info("获取 贷款记录数 : {}", CollectionUtil.size(loanRecords));
    }
}
