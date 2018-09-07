package core;

import com.google.gson.*;

/**
 * Write a description of class core.Profile here.
 *
 * @author Alfredo Natale 
 * @version 0.1
 */
public class Bitstamp extends Profile
{
    private Integer costumer_id;
    private JsonObject ticker;
    private JsonObject hourly_ticker;
    private JsonObject account_balance;
    // Public data functions
    private final String TICKER = "https://www.bitstamp.net/api/v2/ticker/";
    private final String HOURLY_TICKER = "https://www.bitstamp.net/api/v2/ticker_hour/";
    private final String ORDER_BOOK = "https://www.bitstamp.net/api/v2/order_book/";
    private final String TRANSACTIONS = "https://www.bitstamp.net/api/v2/transactions/";
    private final String TRADING_PAIRS_INFO = "https://www.bitstamp.net/api/v2/trading-pairs-info/";
    private final String EUR_USD_CONV = "https://www.bitstamp.net/api/eur_usd/";
    // Private functions
    private final String ACCOUNT_BALANCE = "https://www.bitstamp.net/api/v2/balance/";
    private final String USER_TRANSACTIONS = "https://www.bitstamp.net/api/v2/user_transactions/";
    private final String OPEN_ORDERS = "https://www.bitstamp.net/api/v2/open_orders/";
    private final String ORDER_STATUS = "https://www.bitstamp.net/api/order_status/";
    private final String CANCEL_ORDER = "https://www.bitstamp.net/api/v2/cancel_order/";
    private final String CANCEL_ALL_ORDERS = "https://www.bitstamp.net/api/cancel_all_orders/";
    private final String BUY_LIMIT = "https://www.bitstamp.net/api/buy/";
    private final String BUY_MARKET = "https://www.bitstamp.net/api/v2/buy/market/";
    private final String BUY_INSTANT = "https://www.bitstamp.net/api/v2/buy/instant/";
    private final String SELL_LIMIT = "https://www.bitstamp.net/api/v2/sell/";
    private final String SELL_MARKET = "https://www.bitstamp.net/api/v2/sell/market/";
    private final String SELL_INSTANT ="https://www.bitstamp.net/api/v2/sell/instant/";
    private final String WITHDRAWAL_REQUEST = "https://www.bitstamp.net/api/v2/withdrawal-requests/";
    private final String BITCOIN_WITHDRAWAL = "https://www.bitstamp.net/api/bitcoin_withdrawal/";
    private final String LITECOIN_WITHDRAWAL = "https://www.bitstamp.net/api/v2/ltc_withdrawal/";
    private final String LITECOIN_DEPOSIT_ADDRESS = "https://www.bitstamp.net/api/v2/ltc_address/";
    private final String ETH_WITHDRAWAL = "https://www.bitstamp.net/api/v2/eth_withdrawal/";
    private final String ETH_DEPOSIT_ADDRESS = "https://www.bitstamp.net/api/v2/eth_address/";
    private final String BITCOIN_DEPOSIT_ADDRESS = "https://www.bitstamp.net/api/bitcoin_deposit_address/";
    private final String UNCONFIRMED_BITCOIN_DEPOSITS = "https://www.bitstamp.net/api/unconfirmed_btc/";
    private final String RIPPLE_WITHDRAWAL = "https://www.bitstamp.net/api/ripple_withdrawal/";
    private final String RIPPLE_DEPOSIT_ADDRESS = "https://www.bitstamp.net/api/ripple_address/";
    private final String BCH_WITHDRAWAL = "https://www.bitstamp.net/api/v2/bch_withdrawal/";
    private final String BCH_DEPOSIT_ADDRESS = "https://www.bitstamp.net/api/v2/bch_address/";
    private final String TRANSFER_BALANCE_SUB_TO_MAIN = "https://www.bitstamp.net/api/v2/transfer-to-main/";
    private final String TRANSFER_BALANCE_MAIN_TO_SUB = "https://www.bitstamp.net/api/v2/transfer-from-main/";
    private final String XRP_WITHDRAWAL = "https://www.bitstamp.net/api/v2/xrp_withdrawal/";
    private final String XRP_DEPOSIT_ADDRESS = "https://www.bitstamp.net/api/v2/xrp_address/";
    private final String OPEN_BANK_WITHDRAWAL = "https://www.bitstamp.net/api/v2/withdrawal/open/";
    private final String BANK_WITHDRAWAL_STATUS = "https://www.bitstamp.net/api/v2/withdrawal/status/";
    private final String CANCEL_BANK_WITHDRAWAL = "https://www.bitstamp.net/api/v2/withdrawal/cancel/";
    private final String NEW_LIQUIDATION_ADDRESS = "https://www.bitstamp.net/api/v2/liquidation_address/new/";
    private final String LIQUIDATION_ADDRESS_INFO = "https://www.bitstamp.net/api/v2/liquidation_address/info/";

    /**
     *
     * @param name Account holder name
     * @param costumer_id BitStamp costumer id
     * @param api_key
     * @param api_secret
     * @param pair
     */
    public Bitstamp(String name, Integer costumer_id, String api_key, String api_secret, String pair )
    {
        super(name, api_key, api_secret, pair);
        this.costumer_id = costumer_id;
        ticker = Utils.getReq(TICKER+pair);
        hourly_ticker = Utils.getReq(HOURLY_TICKER+pair);
        account_balance = Utils.postReq(ACCOUNT_BALANCE,
                "key", api_key,
                "signature", signature(),
                "nonce", timestamp());
    }

    /**
     * Buy instant order
     * @param amount Amount
     * @return true if the operation succeded
     */
    public JsonObject buy(String amount)
    {
        JsonObject response = Utils.postReq(BUY_MARKET +getPair(),
                "key", getApi_key(),
                "signature", signature(),
                "nonce", timestamp(),
                "amount", amount);
        return response;
    }

    /**
     * Order status
     * @param id Order ID
     * @return status, transactions
     */
    public JsonObject orderStatus(String id)
    {
        JsonObject response = Utils.postReq(ORDER_STATUS+getPair(),
                "key", getApi_key(),
                "signature", signature(),
                "nonce", timestamp(),
                "id", id);
        return response;
    }

    /**
     * Cancel order
     * @param id Order ID
     * @return id, amount, price, type
     */
    public JsonObject cancelOrder(String id)
    {
        JsonObject response = Utils.postReq(CANCEL_ORDER,
                "key", getApi_key(),
                "signature", signature(),
                "nonce", timestamp(),
                "id", id);
        return response;
    }

    public boolean cancelAllOrders()
    {
        JsonObject response = Utils.postReq(CANCEL_ALL_ORDERS,
                "key", getApi_key(),
                "signature", signature(),
                "nonce", timestamp());
        if (response == null)
            return false;
        return response.getAsJsonPrimitive().getAsBoolean();
    }



    /**
     *
     * @return the Bitstamp signature
     */
    public String signature()
    {
        String message = timestamp() + costumer_id + getApi_key();
        return Utils.hmacDigest(message, getApi_secret(), "HmacSHA256").toUpperCase();
    }

    public static void main(String args[]) {


    }

}