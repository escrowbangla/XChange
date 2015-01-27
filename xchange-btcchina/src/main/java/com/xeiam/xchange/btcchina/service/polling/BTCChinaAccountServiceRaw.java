package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcchina.BTCChina;
import com.xeiam.xchange.btcchina.dto.BTCChinaID;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.account.BTCChinaAccountInfo;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaGetAccountInfoRequest;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaGetDepositsRequest;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaGetWithdrawalRequest;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaGetWithdrawalsRequest;
import com.xeiam.xchange.btcchina.dto.account.request.BTCChinaRequestWithdrawalRequest;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaGetAccountInfoResponse;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaGetDepositsResponse;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaGetWithdrawalResponse;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaGetWithdrawalsResponse;
import com.xeiam.xchange.btcchina.dto.account.response.BTCChinaRequestWithdrawalResponse;

/**
 * @author ObsessiveOrange
 */
public class BTCChinaAccountServiceRaw extends BTCChinaBasePollingService<BTCChina> {

  /**
   * Constructor
   *
   * @param exchange
   */
  // TODO look at this
  public BTCChinaAccountServiceRaw(Exchange exchange) {

    super(BTCChina.class, exchange);
  }

  public BTCChinaGetAccountInfoResponse getBTCChinaAccountInfo() throws IOException {

    return checkResult(btcChina.getAccountInfo(signatureCreator, exchange.getNonceFactory(), new BTCChinaGetAccountInfoRequest()));
  }

  public BTCChinaGetAccountInfoResponse getBTCChinaAccountInfo(String type) throws IOException {

    return checkResult(btcChina.getAccountInfo(signatureCreator, exchange.getNonceFactory(), new BTCChinaGetAccountInfoRequest(type)));
  }

  public BTCChinaGetDepositsResponse getDeposits(String currency) throws IOException {

    return getDeposits(currency, true);
  }

  public BTCChinaGetDepositsResponse getDeposits(String currency, boolean pendingOnly) throws IOException {

    BTCChinaGetDepositsRequest request = new BTCChinaGetDepositsRequest(currency, pendingOnly);
    BTCChinaGetDepositsResponse response = btcChina.getDeposits(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  public BTCChinaGetWithdrawalResponse getWithdrawal(long id) throws IOException {

    return getWithdrawal(id, "BTC");
  }

  public BTCChinaGetWithdrawalResponse getWithdrawal(long id, String currency) throws IOException {

    BTCChinaGetWithdrawalRequest request = new BTCChinaGetWithdrawalRequest(id, currency);
    BTCChinaGetWithdrawalResponse response = btcChina.getWithdrawal(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  public BTCChinaGetWithdrawalsResponse getWithdrawals(String currency) throws IOException {

    return getWithdrawals(currency, true);
  }

  public BTCChinaGetWithdrawalsResponse getWithdrawals(String currency, boolean pendingOnly) throws IOException {

    BTCChinaGetWithdrawalsRequest request = new BTCChinaGetWithdrawalsRequest(currency, pendingOnly);
    BTCChinaGetWithdrawalsResponse response = btcChina.getWithdrawals(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  /**
   * @deprecated use {@link #withdrawBTCChinaFunds(String, BigDecimal, String)}
   *             instead.
   */
  @Deprecated
  public BTCChinaResponse<BTCChinaID> withdrawBTCChinaFunds(BigDecimal amount, String address) throws IOException {

    return checkResult(btcChina.requestWithdrawal(signatureCreator, exchange.getNonceFactory(), new BTCChinaRequestWithdrawalRequest(amount)));
  }

  public BTCChinaResponse<BTCChinaID> withdrawBTCChinaFunds(String currency, BigDecimal amount, String address) throws IOException {

    BTCChinaRequestWithdrawalRequest request = new BTCChinaRequestWithdrawalRequest(currency, amount);
    BTCChinaRequestWithdrawalResponse response = btcChina.requestWithdrawal(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  public String requestBTCChinaDepositAddress(String currency) throws IOException {

    BTCChinaResponse<BTCChinaAccountInfo> response = getBTCChinaAccountInfo(BTCChinaGetAccountInfoRequest.PROFILE_TYPE);

    return response.getResult().getProfile().getDepositAddress(currency);
  }

  /**
   * @deprecated Use {@link #requestBTCChinaDepositAddress(String)} instead.
   */
  @Deprecated
  public String requestBTCChinaBitcoinDepositAddress() throws IOException {

    return requestBTCChinaDepositAddress("btc");
  }

}
