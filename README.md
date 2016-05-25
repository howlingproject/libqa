# libqa™ Project  
Keyword based Social Q&A Community  (http://www.libqa.com)

### Social Q&A & Knowledge sharing platform based GLiDER Wiki. created by GLiDER Wiki Team(http://www.gliderwiki.org)

Howling™  - Social Q&A & Wiki for Community Version
Howling™ is free software, available under the terms of a MIT license.(http://opensource.org/licenses/mit-license.php)

DB Configuration (for mysql)
=======
```
cd /usr/local/mysql/bin 
./mysql -u root -p 

GRANT ALL PRIVILEGES on howling.* to 'howlingproject'@'localhost' identified by 'libqa2014';
GRANT ALL PRIVILEGES on howling.* to 'howlingproject'@'%' identified by 'libqa2014';
flush privileges;

CREATE SCHEMA `howling` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
```

### developer 
QuicK (performizer@gmail.com)
Zune (alivejune@gmail.com)
Songanji (songanji@gmail.com)
Yonghee (seeds.pear.jr@gmail.com)
