# 4장

## querydsl을 사용하기 위한 build.gradle 설정방법

- 다음 참고 : https://ittrue.tistory.com/293

```
// (1) queryDSL 추가
buildscript {
	ext {
		queryDslVersion = "5.0.0"
	}
}

plugins {
	
	...
	
	// (2) queryDSL 추가
	id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
}

...

dependencies {
	
	...
	
	// (3) queryDSL 추가
	implementation "com.querydsl:querydsl-jpa:${queryDslVersion}"
	annotationProcessor "com.querydsl:querydsl-apt:${queryDslVersion}"

	...
	
}

...

// (4) queryDSL 추가 : QueryDSL 빌드 옵션
def querydslDir = "$buildDir/generated/querydsl"

querydsl {
	jpa = true
	querydslSourcesDir = querydslDir
}
sourceSets {
	main.java.srcDir querydslDir
}
configurations {
	querydsl.extendsFrom compileClasspath
}
compileQuerydsl {
	options.annotationProcessorPath = configurations.querydsl
}
// 여기까지

```

## 내가 구현해야할 기능들
- 목록
- 방명록 등록
- 방명록 조회
- 방명록 삭제
- 방명록 수정
- 검색 (목록, 조회, 수정)

## api
- /guestbook/list
- /guestbook/read?gno= &page=
- /guestbook/remove?gno= 
- /guestbook/modify?gno= &page=
- /guestbook/list?type= &keyword= : 검색 

## jQuery를 이용해서 form의 전달 내용을 변경하는 방법

- 참고: https://januaryman.tistory.com/205

## RedirectAttributes를 이용해서 파라미터 전달하기

- 참고 : https://mine-it-record.tistory.com/416