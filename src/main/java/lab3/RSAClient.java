package lab3;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAKeyGenParameterSpec;

public class RSAClient {

    private static final BigInteger ONE = new BigInteger("1");

    static BigInteger r;
    static BigInteger m;

    /**
     * Produces and returns an RSA keypair (N,e,d)
     * N: Modulus, e: Public exponent, d: Private exponent
     * The public exponent value is set to 3 and the keylength to 2048
     * @return RSA keypair
     */
    public KeyPair produceKeyPair() {
        try {
            KeyPairGenerator rsaKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
            RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(2048, BigInteger.valueOf(3));
            rsaKeyPairGenerator.initialize(spec);
            return rsaKeyPairGenerator.generateKeyPair();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Calculates and returns the mu
     * Bob uses ALice's public key and a random value r, such that r is relatively prime to N
     * to compute the blinding factor r^e mod N. Bob then computes the blinded message mu = H(msg) * r^e mod N
     * It is important that r is a random number so that mu does not leak any information about the actual message
     * @return the blinded messahe mu
     * @param publicKey
     * @param publicMessage
     */
    public BigInteger calculateMu(RSAPublicKey publicKey, String publicMessage) {
        try{
            String message = DigestUtils.sha1Hex(publicMessage);
            m = new BigInteger(message.getBytes("UTF8"));

            BigInteger e = publicKey.getPublicExponent();
            BigInteger N = publicKey.getModulus();

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            byte[] randomBytes = new byte[10]; //create byte array to store the r

            BigInteger gcd = null;
            do {
                random.nextBytes(randomBytes);
                r = new BigInteger(randomBytes);

                gcd = r.gcd(N);
            }
            //repeat until getting an r that satisfies all the conditions and belongs to Z*n and >1
            while (!gcd.equals(ONE) || r.compareTo(N) >= 0 || r.compareTo(ONE) <= 0);

            return  ((r.modPow(e, N)).multiply(m)).mod(N);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Calculate mu' using the Chinese Remainder Theorem for optimization
     * Thanks to the isomorphism property f(x+y)=f(x)+f(y) we can split the mu^d modN in two:
     * one mode p , one mode q, and then we can combine the results to calculate muPrime
     *
     * @param mu
     * @param privateCrtKey
     * @param N  - keypair modulus
     * @return mu'
     */
    public BigInteger calculateMuPrimeWithChineseRemainderTheorem(BigInteger mu, RSAPrivateCrtKey privateCrtKey, BigInteger N) {
        BigInteger P = privateCrtKey.getPrimeP();
        BigInteger Q = privateCrtKey.getPrimeQ();

        //We split the mu^d modN in two , one mode p , one mode q
        BigInteger PinverseModQ = P.modInverse(Q);
        BigInteger QinverseModP = Q.modInverse(P);

        BigInteger d = privateCrtKey.getPrivateExponent();

        BigInteger m1 = mu.modPow(d, N).mod(P); // m1=(mu^d modN)modP
        BigInteger m2 = mu.modPow(d, N).mod(Q); // m2=(mu^d modN)modQ

        // muPrime = mu' = (m1*Q*QinverseModP + m2*P*PinverseModQ) mod N where N =P*Q
        return ((m1.multiply(Q).multiply(QinverseModP)).add(m2.multiply(P).multiply(PinverseModQ))).mod(N);
    }

    /**
     * Calculate signature over mu'
     * Bob receives the signature over the blinded message that he sent to Alice
     * and removes the blinding factor to compute the signature over his actual message
     * @param muPrime
     * @param N - keypair modulus
     * @return signature
     */
    public String signatureCalculation(BigInteger muPrime, BigInteger N)
    {
        BigInteger s = r.modInverse(N).multiply(muPrime).mod(N);

        //encode with Base64 encoding to be able to read all the symbols
        byte[] bytes = new Base64().encode(s.toByteArray());
        return new String(bytes);
    }

    /**
     * Checks if the signature received from Alice, is a valid signature for the message given, this can be easily computed because(m^d)^e modN = m
     * @param signature
     * @param publicKey
     */
    public void verify(String signature, RSAPublicKey publicKey) {

        byte[] decodedBytes = new Base64().decode(signature.getBytes());

        BigInteger sign = new BigInteger(decodedBytes);

        BigInteger e = publicKey.getPublicExponent();//get the public exponent of Alice's key pair

        // m = sign^e modN
        BigInteger signedMessageBigInt = sign.modPow(e, publicKey.getModulus());

        String signedMessage = new String(signedMessageBigInt.toByteArray());
        String initialMessage = new String(m.toByteArray());
        if (signedMessage.equals(initialMessage)) {
            System.out.println("Verification of signature completed successfully"); //print message for successful verification of the signature
        } else {
            System.out.println("Verification of signature failed"); // print message for unsuccessful verification of the signature
        }
    }

}
