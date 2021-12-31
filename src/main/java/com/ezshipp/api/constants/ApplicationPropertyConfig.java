package com.ezshipp.api.constants;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Configuration
@Setter
@Getter
public class ApplicationPropertyConfig {

    private @Value("${google.distance.apikey}")
    String googleApiKey;

    private @Value("${google.places.apikey}")
    String googlePlacesApiKey;

//    private @Value("${reports.path}")
//    String reportsPath;
//
//    private @Value("${scheduledtasks.enable}")
//    boolean enableScheduledTasks;
//
//    private @Value("${pubsub.enable}")
//    boolean enablePubSub;
//
//    private @Value("${solutionsinfini.url}")
//    String solutionsInfiniUrl;
//
//    private @Value("${solutionsinfini.apikey}")
//    String solutionsInfiniApiKey;
//
//    private @Value("${testdata.driverids}")
//    String testDataDriverIds;
//
//    private @Value("${app.charges.extraweight}")
//    Integer extraWeightCharge;
//
//    private @Value("${aws.ezshipp-biker-s3-user.accesskey}")
//    String bikerS3AccessKey;
//
//    private @Value("${aws.ezshipp-biker-s3-user.secretkey}")
//    String bikerS3SecretKey;
//
//    private @Value("${aws.ezshipp-customer-s3-user.accesskey}")
//    String customerS3AccessKey;
//
//    private @Value("${aws.ezshipp-customer-s3-user.secretkey}")
//    String customerS3SecretKey;
//
//    private @Value("${paytm.mid}")
//    String paytmMerchantId;
//
//    private @Value("${paytm.secretkey}")
//    String paytmSecretKey;
//
//    private @Value("${paytm.website}")
//    String paytmWebsite;
//
//    private @Value("${paytm.callbackurl}")
//    String paytmCallbackUrl;
//
//    private @Value("${ipstack.apiurl}")
//    String ipstackApiUrl;
//
//    private @Value("${ipstack.accesskey}")
//    String ipstackAccessKey;
//
//    private @Value("${aws.smtp.host}")
//    String awsEmailHost;
//
//    private @Value("${aws.smtp.username}")
//    String awsEmailUserName;
//
//    private @Value("${aws.smtp.password}")
//    String awsEmailPassword;
//
//    private @Value("${aws.smtp.port}")
//    String awsEmailPort;
//
//    private @Value("${aws.queue.email.name}")
//    String awsQueueName;
//
//    private @Value("${aws.queue.email.arn}")
//    String awsQueueArn;
//
//    private @Value("${aws.queue.sms.name}")
//    String awsSMSQueueName;
//
//    private @Value("${aws.queue.sms.arn}")
//    String awsSMSQueueArn;
//
//    private @Value("${aws.topic.email.name}")
//    String awsTopicName;
//
//    private @Value("${aws.topic.email.arn}")
//    String awsTopicArn;
//
//    private @Value("${aws.topic.sms.name}")
//    String awsSMSTopicName;
//
//    private @Value("${aws.topic.sms.arn}")
//    String awsSMSTopicArn;
//
//    private @Value("${aws.topic.order.name}")
//    String awsOrderTopicName;
//
//    private @Value("${aws.topic.order.arn}")
//    String awsOrderTopicArn;
//
//    private @Value("${aws.restapi-user.accesskey}")
//    String awsAccessKey;
//    private @Value("${aws.restapi-user.secretkey}")
//    String awsSecretKey;
//
//    private @Value("${biker.leave-approval.emails}")
//    String leaveApprovalEmails;
//
//    @Value("classpath:templates/otpEmail-en.txt")
//    private Resource otpEmailRes;
//
//    @Value("classpath:templates/resetPassword-en.txt")
//    private Resource resetPasswordEmailRes;
//
//    @Value("classpath:templates/leaveApprovalEmail-en.txt")
//    private Resource leaveApprovalEmailRes;
//
//    @Value("classpath:templates/cancellationEmail-en.txt")
//    private Resource cancellationEmailRes;
//
//    @Value("classpath:templates/deliveredEmail-en.txt")
//    private Resource deliveredEmailRes;
//
//    @Value("classpath:templates/sms/otpSMS-en.txt")
//    private Resource otpSMSRes;
//
//    @Value("classpath:templates/sms/tempPasswordSMS-en.txt")
//    private Resource resetPasswordSMSRes;
//
//    @Value("classpath:templates/sms/receiverSMS-en.txt")
//    private Resource receiverSMSRes;
//
//    @Value("classpath:templates/welcomeEmail-en.txt")
//    private Resource welcomeEmailRes;
//
//    @Value("${file.upload-dir}")
//    private String uploadDir;
//
//    @Value("${images.upload-dir}")
//    private String imagesUploadDir;
//
//    @Value("${s3.bucket}")
//    private String s3bucket;

}
