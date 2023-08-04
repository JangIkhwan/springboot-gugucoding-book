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