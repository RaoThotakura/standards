//
//  ViewController.m
//  chasepay
//
//  Created by Rao Thotakura on 12/17/25.
//

#import "ViewController.h"

#import <SafariServices/SafariServices.h>
#import <AuthenticationServices/AuthenticationServices.h>

@interface ViewController ()

@property(atomic, strong) SFSafariViewController *safari;
@property(nonatomic,strong) SFAuthenticationSession *authSession;
@property(nonatomic,strong) ASWebAuthenticationSession *aswasession;
@property(atomic,strong) UILabel *dataLabel;
@property(atomic, strong) NSString *token;
@property(copy) NSArray<NSURLQueryItem *> *queryItems;

@end

@implementation ViewController

-(BOOL)displaySafari {

    NSURL *authURL = [NSURL URLWithString:@"https://pwctmdev01.jpmchase.net/cp-merchanthost/#/"];
    /* start SFAuthenticationSession */
     self.authSession = SFAuthenticationSession(url: URL(string: authURL)!, callbackURLScheme: callbackUrl, completionHandler: { (callBack:URL?, error:Error?) in
     guard error == nil, let successURL = callBack else {
         print(error!)
         self.cookieLabel.text = "Error retrieving cookie"
         return
     }
     let cookievalue = getQueryStringParameter(url: (successURL.absoluteString), param: self.cookiename)
     self.cookieLabel.text = (cookievalue == "None") ? "cookie not set" : "Cookie for key " + self.cookiename + ": " +cookievalue! })
     self.session = [[SFAuthenticationSession alloc]  initWithURL:authURL callbackURLScheme:@"MyToken://" completionHandler:_sfach];
    if (@available(iOS 11.0, *)) {
        NSString *redirectScheme = request.redirectURL.scheme;
    }
    SFAuthenticationSession* authenticationVC = [[SFAuthenticationSession alloc] initWithURL:requestURL callbackURLScheme:redirectScheme
     completionHandler:^(NSURL * _Nullable callbackURL,
     NSError * _Nullable error) {
         _authenticationVC = nil;
         if (callbackURL) {
             [_session resumeAuthorizationFlowWithURL:callbackURL];
         } else {
             NSError *safariError = [OIDErrorUtilities errorWithCode:OIDErrorCodeUserCanceledAuthorizationFlow underlyingError:errordescription:nil];
             [_session failAuthorizationFlowWithError:safariError];
         }
     }];
    _authenticationVC = authenticationVC;
    openedSafari = [authenticationVC start];
    NSString *cookieName = @"JSESSIONID";
    NSString *callBackUrl = @"cp-merchanthost://";
    NSString *authUrl = @"https://pwctmdev01.jpmchase.net/cp-merchanthost/#/";
    NSURL *authURL = [NSURL URLWithString:authUrl];
    self.session = [[SFAuthenticationSession alloc]
                    initWithURL:authURL
                    callbackURLScheme:callBackUrl
                    completionHandler:^(NSURL *callBackUrl, NSError *error) {
                        NSString *url = [callBackUrl absoluteString];
                        NSLog(@"CB URL : %@", callBackUrl);
                        NSLog(@"callbackURL...%@",url);
                        NSLog(@"ERROR MSG : %@", error);
                        if (error == nil) {
                            NSLog(@"callbackURL...",url);
                            NSURLComponents *urlComp = [[NSURLComponents alloc] initWithString:url];
                            NSLog(@"Path : ", [urlComp path]);
                            NSURLQueryItem *queryParam = urlComp.queryItems[0];
                            NSLog(@"Name : ", [queryParam name]);
                            NSLog(@"Value : ", [queryParam value]);
                            NSLog(@"Auth task completed...",callbackURL.absoluteString);
                        } else {
                            NSLog(@"ERROR MSG : ", error.description);
                        }
                        NSLog(@"callbackURL...%@",url);
                        NSURLComponents *urlComp = [[NSURLComponents alloc] initWithString:url];
                        NSLog(@"Path : %@", [urlComp path]);
                        NSURLQueryItem *queryParam = urlComp.queryItems[0];
                        NSLog(@"Name : %@", [queryParam name]);
                        NSLog(@"Value : %@", [queryParam value]);
                        NSLog(@"Auth task completed...%@",callBackUrl.absoluteString);
                    }];
     [self.session start];
     if ([SFSafariViewController class]) {
         NSString *sURL = @"https://pwctmdev01.jpmchase.net/cp-merchanthost/#/";
         NSURL *URL = [NSURL URLWithString:sURL];
         SFSafariViewController *safari = [[SFSafariViewController alloc] initWithURL:URL];
         SafariDelegate *safariDelegate = [[SafariDelegate alloc] init];
         safari.delegate = self;
         [self presentViewController:safari animated:YES completion:nil];
         return YES;
     }
    return YES;
}
    
-(void)makeOptionsRequest {
    //Form Data
    NSMutableURLRequest *urlReq = [[NSMutableURLRequest alloc] initWithURL:[NSURL
                                  URLWithString:@"http://localdev.chase.com:8001/service/pwc/merchant/session/v20140921/create.action"]];
    [urlReq setTimeoutInterval:10];
    [urlReq setHTTPMethod:@"OPTIONS"];
/**
    Access-Control-Request-Headers: x-jpmc-csrf-token
    Access-Control-Request-Method: POST
    Origin: http://localhost:8002
    Referer: http://localhost:8002/
    User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36
*/
    [urlReq setValue:@"http://localhost:8002" forHTTPHeaderField:@"Origin"];
    [urlReq setValue:@"http://localhost:8002/" forHTTPHeaderField:@"Referer"];
    [urlReq setValue:@"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36" forHTTPHeaderField:@"User-Agent"];
    [urlReq setValue:@"x-jpmc-csrf-token" forHTTPHeaderField:@"Access-Control-Request-Headers"];
    [urlReq setValue:@"POST" forHTTPHeaderField:@"Access-Control-Request-Method"];
    NSURLSession *session = [NSURLSession sharedSession];
    NSURLSessionDataTask *dataTask = [session dataTaskWithRequest:urlReq completionHandler: ^(NSData *data, NSURLResponse *response, NSError *error){
        NSHTTPURLResponse *httpResp = (NSHTTPURLResponse *)response;
        NSLog(@"OPTIONS Resp : %@", httpResp);
        if (httpResp.statusCode == 200) {
            NSError *parseError = nil;
        } else {
            NSLog(@"ERROR : %@", error);
        }
    }];
    [dataTask resume];
}
    
-(void)makePostRequest {
    //Form Data
    NSMutableURLRequest *urlReq = [[NSMutableURLRequest alloc] initWithURL:[NSURL
                                  URLWithString:@"http://localdev.chase.com:8001/service/pwc/merchant/session/v20140921/create.action"]];
    [urlReq setTimeoutInterval:10];
    [urlReq setHTTPMethod:@"POST"];
    NSString *postString= @"useIPv6=false&sessionTraceId=ac00ab4c-9400-4b61-9f85-6fa0ff32db89&companyId=0005409119&merchantId=700000007619&clientId=CPTESTMERCHANT_ECOM&clientIP=127.0.0.1&pwcRequestId=00123&merchantTransactionId=MerReq-50012&merchantName=Merchant%20Name&methodOfPayment=%5B%22VI%22%5D&returnBillingAddress=false&returnShippingAddress=false&returnCustomerContactInfo=false&shipToPostOfficeBox=false&shipToMilitaryBase=false&type=json&channelId=MWC&applId=PWC&version=1.0&cryptoType=ECOM_LONG";
    [urlReq setHTTPBody:[postString dataUsingEncoding:NSUTF8StringEncoding]];
    [urlReq setValue:@"application/x-www-form-urlencoded; charset=UTF-8" forHTTPHeaderField:@"Content-Type"];
    [urlReq setValue:@"http://localhost:8002" forHTTPHeaderField:@"Origin"];
    [urlReq setValue:@"http://localhost:8002/" forHTTPHeaderField:@"Referer"];
    [urlReq setValue:@"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36" forHTTPHeaderField:@"User-Agent"];
    [urlReq setValue:@"NONE" forHTTPHeaderField:@"x-jpmc-csrf-token"];
    NSURLSession *session = [NSURLSession sharedSession];
    NSURLSessionDataTask *dataTask = [session dataTaskWithRequest:urlReq completionHandler: ^(NSData *data, NSURLResponse *response, NSError *error){
        NSHTTPURLResponse *httpResp = (NSHTTPURLResponse *)response;
        NSLog(@"POST Resp : %@", httpResp);
        if (httpResp.statusCode == 200) {
            NSError *parseError = nil;
            NSDictionary *respDict = [NSJSONSerialization JSONObjectWithData:data options:0 error:&parseError];
            id token = respDict[@"digitalSessionId"];
            [self setToken:respDict[@"digitalSessionId"]];
            NSLog(@"RESPONSE : %@", respDict);
            if ([SFSafariViewController class]) {
                NSString *sURL = @"http://localhost:8002/?merchantSessionToken=";
                sURL = [sURL stringByAppendingString:token];
                NSURL *URL = [NSURL URLWithString:sURL];
                NSLog(@"URL : %@", URL);
                SFSafariViewController *safari = [[SFSafariViewController alloc] initWithURL:URL];
                safari.delegate = self;
                [self presentViewController:safari animated:YES completion:nil];
            }
        } else {
            NSLog(@"ERROR : %@", error);
        }
    }];
    [dataTask resume];
}
    
-(void)getSessionId {
    NSMutableURLRequest *urlReq = [[NSMutableURLRequest alloc] initWithURL:[NSURL
                                  URLWithString:@"https://www.ebags.com/api/chasepay/initiatedigitalsession"]];
    [urlReq setHTTPMethod:@"POST"];
    NSString *postString = @"IncludeBillingAddress=true&IncludeShippingAddress=true&IncludeContactInfo=true";
    [urlReq setHTTPBody:[postString dataUsingEncoding:NSUTF8StringEncoding]];
    [urlReq setValue:@"application/x-www-form-urlencoded; charset=UTF-8" forHTTPHeaderField:@"Content-Type"];
    [urlReq setValue:@"www.ebags.com" forHTTPHeaderField:@"Host"];
    [urlReq setValue:@"keep-alive" forHTTPHeaderField:@"Connection"];
    [urlReq setValue:@"78" forHTTPHeaderField:@"Content-Length"];
    [urlReq setValue:@"application/json, text/javascript, */*; q=0.01" forHTTPHeaderField:@"Accept"];
    [urlReq setValue:@"https://www.ebags.com" forHTTPHeaderField:@"Origin"];
    [urlReq setValue:@"" forHTTPHeaderField:@"X-Requested-With"];
    [urlReq setValue:@"" forHTTPHeaderField:@"User-Agent"];
    [urlReq setValue:@"" forHTTPHeaderField:@"Referer"];
    [urlReq setValue:@"" forHTTPHeaderField:@"Accept-Encoding"];
    [urlReq setValue:@"" forHTTPHeaderField:@"Accept-Language"];
    NSString *cookie = @"__SessionId=2brysc2i5iqwtmvye3cjtsh0; __vid=002E84BB261AF44D94482713FD2A484F; _ga=GA1.2.1302048962.1536066854; _gid=GA1.2.1434053521.1536066854; RES_TRACKINGID=867625693783696; ResonanceSegment=1; fps=false; IR_gbd=ebags.com; scarab.visitor=%22799301771C99DBEB%22; _vz=viz_5b8e8529de3ae; _bcvm_vid_909328904499823534=339873942518336650T488FAFF1177F5410435CDF93D5B976A50944CC8858308D598C2FAF52E04F192842CC86F3EDACE6EA96325E27CE63549C955EB279A904FF1F8D8CA9E5EEBF71A9; _bcvm_vrid_909328904499823534=339873941640048486TC088C1708D4BF22F296C14A7636672BD0DE9FD757CF98AFA2E8A17C528760DA21E784D01432C9121A35D2B10212CFF3A583FB25B236CE8EC101BA98D02CCB1D1; extole_access_token=FNR6D086HQ8LG78J4OM0T5S9O6; __gads=ID=c98e2e552c924072:T=1536066857:S=ALNI_MaceeJADGct2Aq8NlEvthu_qosA1A; pl=0; inptime0_14379_us=0; smtrrmkr=636716636860933558%5E06ba7c68-44b0-e811-818c-b8590b7b79f2%5E07ba7c68-44b0-e811-818c-b8590b7b79f2%5E0%5E159.53.46.144; scarab.mayAdd=%5B%7B%22i%22%3A%2210618664%22%7D%5D; scarab.profile=%2210618664%7C1536066891%22; IR_3588=1536066898166%7C162099%7C1536066856420; IR_PI=1536066856420.r36h19hvxlk%7C1536153298166; bc_pv_end=; _pk_id.240.63b3=1022b879249076a0.1536066856.1.1536066899.1536066856.; fanplayr=%7B%22uuid%22%3A%221536066860399-326a1a21a2cc478299e85865%22%2C%22uk%22%3A%228d5eb1a68ff65133b743bda0bcb59790%22%2C%22sk%22%3A%228cdd640e8a58484259718c2c41cf96b4%22%2C%22se%22%3A%22e1.fanplayr.com%22%2C%22fp%22%3A%22c2b27af4270aed5b4f86d3b40cb0d72d_v2%22%2C%22t%22%3A1536066900173%7D; _gali=masterContainer; _gat_UA-43225205-1=1";

    [urlReq setValue:cookie forHTTPHeaderField:@"Cookie"];
    NSURLSession *session = [NSURLSession sharedSession];
    NSURLSessionDataTask *dataTask = [session dataTaskWithRequest:urlReq completionHandler: ^(NSData *data, NSURLResponse *response, NSError *error){
        NSHTTPURLResponse *httpResp = (NSHTTPURLResponse *)response;
        if (httpResp.statusCode == 200) {
            NSError *parseError = nil;
            NSDictionary *respDict = [NSJSONSerialization JSONObjectWithData:data options:0 error:&parseError];
            id token = respDict[@"Data"];
            NSLog(@"RESPONSE : %@", token);
            _dataLabel = token;
            UIAlertController *alert  = [UIAlertController alertControllerWithTitle:@"Digital Session" message:token preferredStyle:UIAlertControllerStyleAlert];
            UIAlertAction *okButton  = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {}];
            [alert addAction:okButton];
            [self presentViewController:alert animated:YES completion:nil];
            if ([SFSafariViewController class]) {
                NSString *sURL = @"http://localhost:8002/?merchantSessionToken=";
                sURL = [sURL stringByAppendingString:token];
                NSURL *URL = [NSURL URLWithString:sURL];
                NSLog(@"URL : %@", URL);
                SFSafariViewController *safari = [[SFSafariViewController alloc] initWithURL:URL];
                safari.delegate = self;
                [self presentViewController:safari animated:YES completion:nil];
            }
          } else {
            NSLog(@"ERROR : %@", error);
        }
    }];
    [dataTask resume];
}
    
-(void)displaySFAuthSession {

    NSString *authUrl = @"http://localdev.chase.com:8001/service/auth/fcc/login";
    NSString *callbackURL = @"PoCButton://service/pwc/checkout/secure/profile/v20140921/list.action";
    NSString *redirect_to = @"&redirect_to=service/pwc/checkout/secure/profile/v20140921/list.action";
    authUrl = [authUrl stringByAppendingString:redirect_to];
    NSURLComponents *urlComp = [[NSURLComponents alloc] initWithString:authUrl];
    NSURLQueryItem *cookie =     [NSURLQueryItem queryItemWithName:@"cookie" value:@"v1st=12AB18FAF62C5635"];
    NSURLQueryItem *formData = [NSURLQueryItem queryItemWithName:@"formdata" value:@"auth_contextId=checkout&auth_deviceCookie=adtoken&auth_deviceSignature=%7B%22navigator%22%3A%7B%22vendorSub%22%3A%22%22%2C%22productSub%22%3A%2220030107%22%2C%22vendor%22%3A%22Google+Inc.%22%2C%22maxTouchPoints%22%3A0%2C%22hardwareConcurrency%22%3A8%2C%22cookieEnabled%22%3Atrue%2C%22appCodeName%22%3A%22Mozilla%22%2C%22appName%22%3A%22Netscape%22%2C%22appVersion%22%3A%225.0+(Macintosh%3B+Intel+Mac+OS+X+10_13_6)+AppleWebKit%2F537.36+(KHTML%2C+like+Gecko)+Chrome%2F68.0.3440.106+Safari%2F537.36%22%2C%22platform%22%3A%22MacIntel%22%2C%22product%22%3A%22Gecko%22%2C%22userAgent%22%3A%22Mozilla%2F5.0+(Macintosh%3B+Intel+Mac+OS+X+10_13_6)+AppleWebKit%2F537.36+(KHTML%2C+like+Gecko)+Chrome%2F68.0.3440.106+Safari%2F537.36%22%2C%22language%22%3A%22en-US%22%2C%22onLine%22%3Atrue%7D%2C%22plugins%22%3A%5B%5D%2C%22screen%22%3A%7B%22availHeight%22%3A877%2C%22availWidth%22%3A1370%2C%22colorDepth%22%3A24%2C%22height%22%3A900%2C%22pixelDepth%22%3A24%2C%22width%22%3A1440%7D%2C%22extra%22%3A%7B%22javascript_ver%22%3A%22%22%2C%22timezone%22%3A300%7D%7D&auth_otpreason=2&auth_passwd=wwerwerwew&auth_passwd_org=wwerwerwew&auth_userId=1234&auth_regdevicetoken=1234567&auth_prnturl=http%3A%2F%2Flocalhost%3A8002%2F&RGBLogon=LOB&Referrer=https%3A%2F%2Fwww.chase.com&type=json&auth_siteId=CWC"];
    NSURLQueryItem *redirect_uri = [NSURLQueryItem queryItemWithName:@"redirect_uri" value:@"PoCButton://service/pwc/checkout/secure/profile/v20140921/list.action"];
    NSURLQueryItem *response_type =     [NSURLQueryItem queryItemWithName:@"response_type" value:@"code"];
    urlComp.queryItems = @[cookie,formData,redirect_uri,response_type];
    authUrl = [authUrl stringByAppendingString:@"?callbackUrl="];
    authUrl = [authUrl stringByAppendingString:callBackUrl];\
    NSURL *authURL = [NSURL URLWithString:authUrl];
    self.session = [[SFAuthenticationSession alloc]
                    initWithURL:urlComp.URL
                    callbackURLScheme:callbackURL
                    completionHandler:^(NSURL *callBack, NSError *error) {
                        NSLog(@"ERROR MSG : %@", error);
                        NSURL *successUrl = [callBack absoluteURL];
                        NSString *successStr = [callBack absoluteString];
                        NSLog(@"CB URL : %@", callbackURL);
                        NSLog(@"callbackURL...%@",successUrl);
                        NSLog(@"callbackURL...%@",successStr);
                        NSLog(@"callBack : %@", callBack);
                        NSURLComponents *urlComp = [[NSURLComponents alloc] initWithString:successUrl];
                        NSLog(@"Path : %@", [urlComp path]);
                        NSURLQueryItem *queryParam = urlComp.queryItems[0];
                        NSLog(@"Name : %@", [queryParam name]);
                        NSLog(@"Value : %@", [queryParam value]);
                        NSLog(@"Auth task completed...%@",callBack.absoluteString);
                        NSLog(@"safariViewControllerDidFinish");
                        NSLog(@"Application Dismissed.. Exiting ViewController");
                        NSString *returnToButton = @"Exit From ChasePay LightBox";
                        NSDictionary *data = [NSDictionary dictionaryWithObject:returnToButton forKey:@"key"];
                        [[NSNotificationCenter defaultCenter] postNotificationName:@"cpcCallback" object:self userInfo:data];
                    }];
    [self.session start];
}
    
-(void)displayASWAuthSession {

    NSMutableURLRequest *authUrl = [[NSMutableURLRequest alloc] initWithURL:[NSURL
                                   URLWithString:@"http://localdev.chase.com:8001/service/pwc/merchant/session/v20140921/create.action"]];
    [authUrl setTimeoutInterval:10];
    [authUrl setHTTPMethod:@"POST"];
    NSString *callbackURL = @"PoCButton://service/pwc/checkout/secure/profile/v20140921/list.action";
    NSString *postString= @"useIPv6=false&sessionTraceId=ac00ab4c-9400-4b61-9f85-6fa0ff32db89&companyId=0005409119&merchantId=700000007619&clientId=CPTESTMERCHANT_ECOM&clientIP=127.0.0.1&pwcRequestId=00123&merchantTransactionId=MerReq-50012&merchantName=Merchant%20Name&methodOfPayment=%5B%22VI%22%5D&returnBillingAddress=false&returnShippingAddress=false&returnCustomerContactInfo=false&shipToPostOfficeBox=false&shipToMilitaryBase=false&type=json&channelId=MWC&applId=PWC&version=1.0&cryptoType=ECOM_LONG";
     [authUrl setHTTPBody:[postString dataUsingEncoding:NSUTF8StringEncoding]];
     [authUrl setValue:@"application/x-www-form-urlencoded; charset=UTF-8" forHTTPHeaderField:@"Content-Type"];
     [authUrl setValue:@"http://localhost:8002" forHTTPHeaderField:@"Origin"];
     [authUrl setValue:@"http://localhost:8002/" forHTTPHeaderField:@"Referer"];
     [authUrl setValue:@"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36" forHTTPHeaderField:@"User-Agent"];
     [authUrl setValue:@"NONE" forHTTPHeaderField:@"x-jpmc-csrf-token"];
    NSURLSession *session = [NSURLSession sharedSession];
    NSURLSessionDataTask *dataTask = [session dataTaskWithRequest:authUrl completionHandler: ^(NSData *data, NSURLResponse *response, NSError *error){
        NSHTTPURLResponse *httpResp = (NSHTTPURLResponse *)response;
        if (httpResp.statusCode == 200) {
            NSError *parseError = nil;
            NSDictionary *respDict = [NSJSONSerialization JSONObjectWithData:data options:0 error:&parseError];
            id token = respDict[@"digitalSessionId"];
            [self setToken:respDict[@"digitalSessionId"]];
            NSLog(@"RESPONSE : %@", respDict);
            NSString *sURL = @"http://localhost:8002/?merchantSessionToken=";
            sURL = [sURL stringByAppendingString:token];
            NSURL *uURL = [NSURL URLWithString:sURL];
            NSLog(@"URL : %@", uURL);
            self.aswasession = [[ASWebAuthenticationSession  alloc] initWithURL:uURL
                                  callbackURLScheme:callbackURL
                                  completionHandler:^(NSURL *callBack, NSError *error) {
                                      NSLog(@"ERROR MSG : %@", error);
                                      NSURL *successUrl = [callBack absoluteURL];
                                      NSString *successStr = [callBack absoluteString];
                                      NSLog(@"CB URL : %@", callbackURL);
                                      NSLog(@"callbackURL...%@",successUrl);
                                      NSLog(@"callbackURL...%@",successStr);
                                      NSLog(@"callBack : %@", callBack);
                                      NSLog(@"safariViewControllerDidFinish");
                                      NSLog(@"Application Dismissed.. Exiting ViewController");
                                      NSString *returnToButton = @"Exit From ChasePay LightBox";
                                      NSDictionary *data = [NSDictionary dictionaryWithObject:returnToButton forKey:@"key"];
                                      [[NSNotificationCenter defaultCenter] postNotificationName:@"cpcCallback" object:self userInfo:data];
                                  }];
            [self.aswasession start];
        } else {
            NSLog(@"ERROR : %@", error);
        }
    }];

    [dataTask resume];
    NSURLComponents *urlComp = [[NSURLComponents alloc] initWithString:authUrl];
    NSURLQueryItem *redirect_uri =     [NSURLQueryItem queryItemWithName:@"redirect_uri" value:@"PoCButton://service/pwc/checkout/secure/profile/v20140921/list.action"];
    NSURLQueryItem *response_type =     [NSURLQueryItem queryItemWithName:@"response_type" value:@"code"];
    urlComp.queryItems = @[redirect_uri,response_type];
    NSMutableURLRequest *urlReq = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:@"http://localdev.chase.com:8001/service/pwc/merchant/session/v20140921/create.action"]];
    [urlReq setTimeoutInterval:10];
    [urlReq setHTTPMethod:@"POST"];
    NSString *postString= @"useIPv6=false&sessionTraceId=ac00ab4c-9400-4b61-9f85-6fa0ff32db89&companyId=0005409119&merchantId=700000007619&clientId=CPTESTMERCHANT_ECOM&clientIP=127.0.0.1&pwcRequestId=00123&merchantTransactionId=MerReq-50012&merchantName=Merchant%20Name&methodOfPayment=%5B%22VI%22%5D&returnBillingAddress=false&returnShippingAddress=false&returnCustomerContactInfo=false&shipToPostOfficeBox=false&shipToMilitaryBase=false&type=json&channelId=MWC&applId=PWC&version=1.0&cryptoType=ECOM_LONG";
    [urlReq setHTTPBody:[postString dataUsingEncoding:NSUTF8StringEncoding]];
    [urlReq setValue:@"application/x-www-form-urlencoded; charset=UTF-8" forHTTPHeaderField:@"Content-Type"];
    [urlReq setValue:@"http://localhost:8002" forHTTPHeaderField:@"Origin"];
    [urlReq setValue:@"http://localhost:8002/" forHTTPHeaderField:@"Referer"];
    [urlReq setValue:@"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36" forHTTPHeaderField:@"User-Agent"];
    [urlReq setValue:@"NONE" forHTTPHeaderField:@"x-jpmc-csrf-token"];
    NSURLSession *session = [NSURLSession sharedSession];
    NSURLSessionDataTask *dataTask = [session dataTaskWithRequest:urlReq completionHandler: ^(NSData *data, NSURLResponse *response, NSError *error){
        NSHTTPURLResponse *httpResp = (NSHTTPURLResponse *)response;
        if (httpResp.statusCode == 200) {
            NSError *parseError = nil;
            NSDictionary *respDict = [NSJSONSerialization JSONObjectWithData:data options:0 error:&parseError];
            id token = respDict[@"digitalSessionId"];
            [self setToken:respDict[@"digitalSessionId"]];
            NSLog(@"RESPONSE : %@", respDict);
            if ([SFSafariViewController class]) {
                NSString *sURL = @"http://localhost:8002/?merchantSessionToken=";
                sURL = [sURL stringByAppendingString:token];
                NSURL *URL = [NSURL URLWithString:sURL];
                NSLog(@"URL : %@", URL);
                SFSafariViewController *safari = [[SFSafariViewController alloc] initWithURL:URL];
                safari.delegate = self;
                [self presentViewController:safari animated:YES completion:nil];
            }
        } else {
            NSLog(@"ERROR : %@", error);
        }
    }];
    [dataTask resume];
    self.aswasession = [[ASWebAuthenticationSession  alloc] initWithURL:urlComp.URL
                    callbackURLScheme:callbackURL
                    completionHandler:^(NSURL *callBack, NSError *error) {
                        NSLog(@"ERROR MSG : %@", error);
                        NSURL *successUrl = [callBack absoluteURL];
                        NSString *successStr = [callBack absoluteString];
                        NSLog(@"CB URL : %@", callbackURL);
                        NSLog(@"callbackURL...%@",successUrl);
                        NSLog(@"callbackURL...%@",successStr);
                        NSLog(@"callBack : %@", callBack);
                        NSLog(@"safariViewControllerDidFinish");
                        NSLog(@"Application Dismissed.. Exiting ViewController");
                        NSString *returnToButton = @"Exit From ChasePay LightBox";
                        NSDictionary *data = [NSDictionary dictionaryWithObject:returnToButton forKey:@"key"];
                        [[NSNotificationCenter defaultCenter] postNotificationName:@"cpcCallback" object:self userInfo:data];
                    }];
    [self.aswasession start];
}
    
-(void)addChasePay {

    UIButton *chasePay = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    chasePay.frame = CGRectMake(110.0, 360.0, 200.0, 50.0);
    chasePay.backgroundColor = [UIColor blueColor];
    [chasePay setTitleColor:[UIColor whiteColor] forState:(UIControlStateNormal)];
    [chasePay setTitle:@"Pay With Chase" forState:UIControlStateNormal];
    [chasePay addTarget:self action:@selector(chasePayAction:) forControlEvents:(UIControlEventTouchUpInside)];
    [self.view addSubview:chasePay];
}
    
-(UILabel *)addDataLabel {

    UILabel *tokenLabel = [[UILabel alloc]initWithFrame:CGRectMake(110.0, 460.0, 200.0, 100.0)];
    tokenLabel.numberOfLines = 1;
    tokenLabel.baselineAdjustment = YES;
    tokenLabel.adjustsFontSizeToFitWidth = YES;
    tokenLabel.adjustsLetterSpacingToFitWidth = YES;
    tokenLabel.clipsToBounds = YES;
    tokenLabel.backgroundColor = [UIColor blueColor];
    tokenLabel.textColor = [UIColor whiteColor];
    tokenLabel.textAlignment = NSTextAlignmentLeft;
    [self.view addSubview:tokenLabel];
    return tokenLabel;
}
    
-(void)chasePayAction:(UIButton*)sender {

    /* Using SFSafariViewController against ebags.com and redirect to localhost:8002 */
    [self getSessionId];
    /*Using SFAuthenticationSession against PRODCERT: pwctmdev01.jpmchase.net/cp-merchanthost */
    [self displaySafari];
    /*Using SFAuthenticationSession against localdev.chase.com*/
    [self displaySFAuthSession];
    /*Using SFSafariViewController against localhost:8002*/
    [self makeOptionsRequest];
    [self makePostRequest];
    /*Using ASWebAuthenticationSession against localhost:8002 */
    [self displayASWAuthSession];
    BOOL isSafariViewSupported = [self displaySafari];
        if (!isSafariViewSupported) {
            UIAlertController *alert  = [UIAlertController alertControllerWithTitle:@"WARNING" message:@"iOS versoin is incompatible" preferredStyle:UIAlertControllerStyleAlert];
            UIAlertAction *okButton  = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {}];
            [alert addAction:okButton];
            [self presentViewController:alert animated:YES completion:nil];
        }
}
    
-(void)settingsFlowAction:(UIButton*)sender {

    if ([SFSafariViewController class]) {
        NSString *sURL = @"http://localhost:9000/#/dashboard/accountSafe/detail/requestExternalFinancialAccounts";
        NSURL *URL = [NSURL URLWithString:sURL];
        NSLog(@"URL : %@", URL);
        SFSafariViewController *safari = [[SFSafariViewController alloc] initWithURL:URL];
        safari.delegate = self;
        [self presentViewController:safari animated:YES completion:nil];
    }
}
    
-(void)loadSettingsFlow {

    UIButton *settingsFlow = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    settingsFlow.frame = CGRectMake(110.0, 460.0, 200.0, 50.0);
    settingsFlow.backgroundColor = [UIColor redColor];
    [settingsFlow setTitleColor:[UIColor whiteColor] forState:(UIControlStateNormal)];
    [settingsFlow setTitle:@"Aggregations Settings Flow" forState:UIControlStateNormal];
    [settingsFlow addTarget:self action:@selector(settingsFlowAction:) forControlEvents:(UIControlEventTouchUpInside)];
    [self.view addSubview:settingsFlow];
}
    
-(void)replaceCardFlowAction:(UIButton*)sender {

    if ([SFSafariViewController class]) {
        NSString *sURL = @"http://localhost:9000/#/dashboard/index";
        NSString *sURL =
                     @"http://localhost:9000/#/dashboard/cardAccountServicingArea/replaceCard/entry;ai=6125189;isDigitalWalletSupported=true";
        NSURL *URL = [NSURL URLWithString:sURL];
        NSLog(@"URL : %@", URL);
        SFSafariViewController *safari = [[SFSafariViewController alloc] initWithURL:URL];
        safari.delegate = self;
        [self presentViewController:safari animated:YES completion:nil];
    }
}
    
-(void)loadReplaceCardFlow {

    UIButton *replaceCardFlow = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    replaceCardFlow.frame = CGRectMake(110.0, 560.0, 200.0, 50.0);
    replaceCardFlow.backgroundColor = [UIColor greenColor];
    [replaceCardFlow setTitleColor:[UIColor blackColor] forState:(UIControlStateNormal)];
    [replaceCardFlow setTitle:@"SSAM: Replace Card Flow" forState:UIControlStateNormal];
    [replaceCardFlow addTarget:self action:@selector(replaceCardFlowAction:) forControlEvents:(UIControlEventTouchUpInside)];
    [self.view addSubview:replaceCardFlow];
}
    
-(void)viewDidAppear: (BOOL)animated {

    [super viewDidAppear:animated];
     BOOL isSafariViewSupported = [self displaySafari];
     if (!isSafariViewSupported) {
     UIAlertController *alert  = [UIAlertController alertControllerWithTitle:@"WARNING" message:@"iOS versoin is incompatible" preferredStyle:UIAlertControllerStyleAlert];
     UIAlertAction *okButton  = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {}];
     [alert addAction:okButton];
     [self presentViewController:alert animated:YES completion:nil];
     }
}
    
-(void)viewDidLoad {

[super viewDidLoad];
    [self addChasePay];
    [self loadSettingsFlow];
    [self loadReplaceCardFlow];
    [self setDataLabel:[self addDataLabel]];
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(cpcCallback:) name:@"cpcCallback" object:nil];
}
    
-(void)cpcCallback: (NSNotification *)notification {

    [self dismissViewControllerAnimated:NO completion:nil];
    NSDictionary *dict = [notification userInfo];
    NSString *value = [dict objectForKey:@"key"];
    NSLog(@"VALUE %@", value);
    UIAlertController *alert  = [UIAlertController alertControllerWithTitle:@"Chasepay Native App" message:value preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *okButton  = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {}];
    [alert addAction:okButton];
    [self presentViewController:alert animated:YES completion:nil];
}
    
-(NSArray<UIActivity *> *)safariViewController:(SFSafariViewController *)controller activityItemsForURL:(NSURL *)URL title:(nullable NSString *)title {

    NSLog(@"safariViewController activityItemsForURL");
    return @[];
}
    
-(void)safariViewControllerDidFinish:(SFSafariViewController *)controller {

    NSLog(@"safariViewControllerDidFinish");
    [controller dismissViewControllerAnimated:true completion:nil];
    NSLog(@"Application Dismissed.. Exiting ViewController");
    NSString *returnToButton = @"Exit From ChasePay LightBox with token digitalSessionId :- ";
    returnToButton = [returnToButton stringByAppendingString:[self token]];
    NSDictionary *data = [NSDictionary dictionaryWithObject:returnToButton forKey:@"key"];
    [[NSNotificationCenter defaultCenter] postNotificationName:@"cpcCallback" object:self userInfo:data];
}
    
-(void)safariViewController:(SFSafariViewController *)controller didCompleteInitialLoad:(BOOL)didLoadSuccessfully {

    NSLog(@"safariViewController didCompleteInitialLoad");
}
    
@end
