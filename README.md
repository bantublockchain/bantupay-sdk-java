# bantupay-sdk-java

[![](https://jitpack.io/v/bantublockchain/bantupay-sdk-java.svg)](https://jitpack.io/#bantublockchain/bantupay-sdk-java)

# Installation

Use [jitpack.io]('https://jitpack.io/#bantublockchain/bantupay-sdk-java/0.1')'s Maven repository:

### Step 1. Add the JitPack repository to your build file

```
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```

### Step 2. Add the dependency

```
<dependency>
	<groupId>com.github.bantublockchain</groupId>
	<artifactId>bantupay-sdk-java</artifactId>
	<version>{version}</version>
</dependency>
```

# Basic Usage and Documentation

> To create a new account (key pair)

```java
import com.bantupay.sdk.createAccount
import com.bantupay.sdk.KeyPairModel

KeyPairModel newAcc = createAccount();

```

> To sign HTTP data (for your initial request to the BantuPay server)

```java
import com.bantupay.sdk.signHTTP

String signedData = signHTTP(
	"/v2/user/",
	"SBVNK4S2NU2QSBDZBKQCGR7DX5FTQFDJVKGWVCZLIEOV4QMANLQYSLNI",
	"{'username': 'proxie'}" // This will gson encoded data
	)

```

> To sign a transaction ( responsd from the server )

```java
import com.bantupay.sdk.signTxn

String signedData = signTxn(
	"SBVNK4S2NU2QSBDZBKQCGR7DX5FTQFDJVKGWVCZLIEOV4QMANLQYSLNI", // secret key
	"AAAAAOKtdgWGQ02FzacmAD1WhAhI5Dp7kPRojOGjNQj3FBWvAAAAZAAcmBgAAAAEAAAAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQAAAAAAAAAFAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEAAAANQmFudHUuTmV0d29yawAAAAAAAAAAAAAAAAAAAA==", // transaction envelop
	"Bantu Testnet" // Network phrase
)

```

# To make express payment

> The expressPay method is designed for making payments from bots or any other API like bulk payment engines, that does not need any user feedback

```java
import com.bantupay.sdk.expressPay
import com.bantupay.sdk.PaymentResponse

// Example for making XBN payment
// Please NOTE: - if you are making payment to Bantu's naitive assest you  MUST leave assetCode and assetIssuer empty, your request will look like this.

PaymentResponse response = expressPay(
	"proxie",
	"long",
	"Test memo",
	"100",
	"SBVNK4S2NU2QSBDZBKQCGR7DX5FTQFDJVKGWVCZLIEOV4QMANLQYSLNI",
	"https://api.bantupay.org"
)


// Let's make a BNR payment
// for mainnet, BNR issuer is GARO4SCJCPO4QPW27EZQBDV5KY4GXXBH6FOLUQ73K73VC4NP5PGBANTU,
// for testnet BNR issuer is GBNRQ56XX4JA3HKXP7EACLLXYPHCYCWBWOQZYSPGUIJSR5JSAD22EZXG
// Please ensure that you use the right issuer

PaymentResponse response = expressPay(
	"proxie",
	"long",
	"Test memo",
	"100",
	"SBVNK4S2NU2QSBDZBKQCGR7DX5FTQFDJVKGWVCZLIEOV4QMANLQYSLNI",
	"https://api.bantupay.org",
	"GARO4SCJCPO4QPW27EZQBDV5KY4GXXBH6FOLUQ73K73VC4NP5PGBANTU",
	"BNR"
)

```

# Another way to make payment

```java
import com.bantupay.sdk.confirmPaymentDetail
import com.bantupay.sdk.PaymentResponse
import com.bantupay.sdk.makePayment
import shadow.com.google.gson.Gson;


Response firstCallResponse = confirmPaymentDetails(
	"proxie",
	"long",
	"Test memo",
	"100",
	"SBVNK4S2NU2QSBDZBKQCGR7DX5FTQFDJVKGWVCZLIEOV4QMANLQYSLNI",
	"https://api.bantupay.org"
)

```

# Note!

> Pass the response of the first call in to the makePayment function.
> confirmPayment will return the respnse gotten from the server, this contains, firstName, lastName, destinationThumbnail, which can be used in stuff like your mobile apps and the likes, networkPhrase, transaction which will be returned in the makePayment and lastly message with will contain info like extra fees or charges that may acrue.

```java
// if success you will get a response otherwise an expception will be thorw with the eerror from the server
PaymentResponse res = null;
try {
	if(res.isSuccessful()){
		res = new Gson().fromJson(response.body().string(), PaymentResponse.class);
		// the we make call to the next method makePayment
		PaymentResponse lastCallRes = makePayment (
			"proxie",
			"long",
			"Test memo",
			"100",
			"SBVNK4S2NU2QSBDZBKQCGR7DX5FTQFDJVKGWVCZLIEOV4QMANLQYSLNI",
			"https://api.bantupay.org",
			"",
			"",
			res,
		)
	}
} catch (JsonSyntaxException e) {
	e.printStackTrace();
} catch (IOException e) {
	e.printStackTrace();
}




```
