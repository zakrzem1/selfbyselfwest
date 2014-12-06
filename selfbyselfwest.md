SelfBySelfWest


==CATCHOOM
http://tinyurl.com/pjcwtop

https://crs.catchoom.com/accounts/signup/

Log in now at https://crs.catchoom.com
Your username: maciek.zakrzewski@gmail.com / HAh@haha

Visit http://catchoom.com/product/craftar/augmented-reality-and-image-recognition-sdk/ to get your copy of the Mobile SDK and Example app for iOS and Android.


add new collection 
	logos


#Collection:
https://my.craftar.net/collections/d7e7d9c407e64221a0bc383689e3bebf/

#item
hackviacom	/Users/zakrzewm/SelfBySelfWest/hack-viacom.png

#api key
 321ac3ab1d3cdb0da29f89745f3f10e2d74b1f6c

 logos collection uuid d7e7d9c407e64221a0bc383689e3bebf
 and the token 3e9d8f72fbb941dc



 curl https://search.craftar.net/v1/search -F "token=3e9d8f72fbb941dc" -F "image=@query.jpg"


 curl https://search.craftar.net/v1/search -F "token=3e9d8f72fbb941dc" -F "image=@/Users/zakrzewm/SelfBySelfWest/dailyshow_photo_1.JPG"



curl https://search.craftar.net/v1/search -F "token=3e9d8f72fbb941dc" -F "image=@/Users/zakrzewm/SelfBySelfWest/dailyshow_photo_3.JPG"
{"search_time": 156, "results": [{"item": {"url": "http://thedailyshow.cc.com/", "content": null, "uuid": "150888159a5947ada62853e1a319a406", "name": "dailyshow", "custom": "https://crs-beta-catchoom.s3.amazonaws.com/collections/d7e7d9c407e64221a0bc383689e3bebf/metadata/150888159a5947ada62853e1a319a406.json"}, "image": {"thumb_120": "https://crs-beta-catchoom.s3.amazonaws.com/cache/collections/d7e7d9c407e64221a0bc383689e3bebf/images/150888159a5947ada62853e1a319a406_08bcd55b6fa34da1bfb94b18b9999b4e_thumb_120.jpe", "uuid": "08bcd55b6fa34da1bfb94b18b9999b4e"}, "score": 36}]}

{
  "search_time": 156,
  "results": [
    {
      "item": {
        "url": "http://thedailyshow.cc.com/",
        "content": null,
        "uuid": "150888159a5947ada62853e1a319a406",
        "name": "dailyshow",
        "custom": "https://crs-beta-catchoom.s3.amazonaws.com/collections/d7e7d9c407e64221a0bc383689e3bebf/metadata/150888159a5947ada62853e1a319a406.json"
      },
      "image": {
        "thumb_120": "https://crs-beta-catchoom.s3.amazonaws.com/cache/collections/d7e7d9c407e64221a0bc383689e3bebf/images/150888159a5947ada62853e1a319a406_08bcd55b6fa34da1bfb94b18b9999b4e_thumb_120.jpe",
        "uuid": "08bcd55b6fa34da1bfb94b18b9999b4e"
      },
      "score": 36
    }
  ]
}


==
Multipart - form data request

zakrzewm@CL01422:~$ curl https://search.craftar.net/v1/search -F "token=3e9d8f72fbb941dc" -F "image=@/Users/zakrzewm/SelfBySelfWest/dailyshow_photo_3.JPG" -v
* Hostname was NOT found in DNS cache
*   Trying 184.169.162.140...
* Connected to search.craftar.net (184.169.162.140) port 443 (#0)
* TLS 1.2 connection using TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256
* Server certificate: *.craftar.net
* Server certificate: COMODO RSA Domain Validation Secure Server CA
* Server certificate: COMODO RSA Certification Authority
* Server certificate: AddTrust External CA Root
> POST /v1/search HTTP/1.1
> User-Agent: curl/7.37.1
> Host: search.craftar.net
> Accept: */*
> Content-Length: 19966
> Expect: 100-continue
> Content-Type: multipart/form-data; boundary=------------------------a2797ea5f6debc9e
>
< HTTP/1.1 100 Continue
< HTTP/1.1 200 OK
< Access-Control-Allow-Origin: *
< Content-Type: application/json
< Date: Fri, 05 Dec 2014 22:07:34 GMT
* Server nginx/1.6.2 is not blacklisted
< Server: nginx/1.6.2
< Content-Length: 578
< Connection: keep-alive
<
* Connection #0 to host search.craftar.net left intact
{"search_time": 149, "results": [{"item": {"url": "http://thedailyshow.cc.com/", "content": null, "uuid": "150888159a5947ada62853e1a319a406", "name": "dailyshow", "custom": "https://crs-beta-catchoom.s3.amazonaws.com/collections/d7e7d9c407e64221a0bc383689e3bebf/metadata/150888159a5947ada62853e1a319a406.json"}, "image": {"thumb_120": "https://crs-beta-catchoom.s3.amazonaws.com/cache/collections/d7e7d9c407e64221a0bc383689e3bebf/images/150888159a5947ada62853e1a319a406_08bcd55b6fa34da1bfb94b18b9999b4e_thumb_120.jpe", "uuid": "08bcd55b6fa34da1bfb94b18b9999b4e"}, "score": 36}]}




==
Created project
git clone https://github.com/zakrzem1/selfbyselfwest.git

mvn archetype:create -DgroupId=selfbyselfwest  -DartifactId=selfbyselfwest

You can clone it 






