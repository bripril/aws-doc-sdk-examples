//snippet-sourcedescription:[SetKeyPolicy.java demonstrates how to set an AWS Key Management Service (AWS KMS) key policy.]
//snippet-keyword:[AWS SDK for Java v2]
//snippet-keyword:[Code Sample]
//snippet-service:[AWS Key Management Service]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[11/02/2020]
//snippet-sourceauthor:[scmacdon-aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.example.kms;

// snippet-start:[kms.java2_set_policy.import]
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.KmsException;
import software.amazon.awssdk.services.kms.model.PutKeyPolicyRequest;
// snippet-end:[kms.java2_set_policy.import]

public class SetKeyPolicy {

    public static void main(String[] args) {

        final String USAGE = "\n" +
                "Usage:\n" +
                "    SetKeyPolicy <keyId> <policyName> \n\n" +
                "Where:\n" +
                "    keyId - a unique identifier for the customer master key (CMK) (for example, xxxxxbcd-12ab-34cd-56ef-1234567890ab). \n\n" +
                "    policyName - the name of the key policy. \n\n" ;

        if (args.length != 2) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String keyId = args[0];
        String policyName = args[1];
        Region region = Region.US_WEST_2;
        KmsClient kmsClient = KmsClient.builder()
                .region(region)
                .build();

        createPolicy(kmsClient, keyId, policyName );
        kmsClient.close();
    }

    // snippet-start:[kms.java2_set_policy.main]
    public static void createPolicy(KmsClient kmsClient, String keyId, String policyName) {
        String policy = "{" +
                "  \"Version\": \"2012-10-17\"," +
                "  \"Statement\": [{" +
                "    \"Effect\": \"Allow\"," +
                // Replace the following user ARN with one for a real user.
                "    \"Principal\": {\"AWS\": \"arn:aws:iam::814548047983:root\"}," +
                "    \"Action\": \"kms:*\"," +
                "    \"Resource\": \"*\"" +
                "  }]" +
                "}";
        try {

            PutKeyPolicyRequest keyPolicyRequest = PutKeyPolicyRequest.builder()
                .keyId(keyId)
                .policyName(policyName)
                .policy(policy)
                .build();

            kmsClient.putKeyPolicy(keyPolicyRequest);
            System.out.println("Done");

        } catch (KmsException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    // snippet-end:[kms.java2_set_policy.main]
}
