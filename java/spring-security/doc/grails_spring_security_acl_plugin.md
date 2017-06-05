# 1. Introduction to the Spring Security ACL Plugin
> The ACL plugin adds Domain Object Security support to a Grails application that uses Spring Security.

ACL プラグインは、 Spring Security を使うドメインオブジェクトセキュリティのサポートを Grails アプリケーションに追加します。

> It depends on the Spring Security Core plugin.

このプラグインは Spring Security のコアプラグインに依存します。

> The core plugin and other extension plugins support restricting access to URLs via rules that include checking a user’s authentication status, roles, etc.

コアプラグインと他の拡張プラグインはユーザの認証ステータス、ロール、その他をチェックするルールを通じて、 URL へのアクセス制限をサポートします。

> and the ACL plugin extends this by adding support for restricting access to individual domain class instances.

そして、 ACL プラグインはこれ（URL へのアクセス制限）を拡張し、個々のドメインクラスのインスタンスへのアクセスを制限するサポートを追加します。

> The access can be very fine-grained and can define which actions can be taken on an object - these typically include Read, Create, Write, Delete, and Administer but you’re free to define whatever actions you like.

アクセスはとてもきめ細かく定義でき、オブジェクトにどのアクションができるか、典型的なものとして Read, Create, Write, Delete そして Administer を指定できます。ただし、あなたの好きなアクションを定義することもできます。

> To learn about using ACLs in Grails, you can follow the Tutorial and in addition you can download and run a complete Grails application that uses the plugin.

チュートリアルを見ることで Grails で ACL の使用について学ぶことができ、さらにプラグインを使用した Grails の完全なアプリケーションをダウンロードすることもできます。

> Installing and running the application are described in Sample Application.

サンプルアプリケーションをインストールしてから実行してください。

> In addition to this document, you should read the Spring Security documentation.

このドキュメントに加えて、 Spring Security のドキュメントも読むべきです。

# 2. Usage
## 2.1. Securing Service Methods
> There are two primary use cases for ACL security: determining whether a user is allowed to perform an action on an instance before the action is invoked, and restricting access to single or multiple instances after methods are invoked (this is typically implemented by collection filtering).

ACL セキュリティについて２つの主要なユースケースがあります。  
アクションが実行される前に、あるインスタンスでのアクションを実行することをユーザに許可するかどうかを決定する、そしてメソッドが実行された後で１つかもしくは複数のインスタンスへのアクセスを制限する（よくコレクションのフィルターで実装される）

> You can call aclUtilService.hasPermission() explicitly, but this tends to clutter your code with security logic that often has little to do with business logic.

`aclUtilService.hasPermission()` を明示的に呼ぶことができます。しかし、ビジネスロジックと関係のないセキュリティロジックを一緒にするとコードは複雑になる傾向があります。

> Instead, Spring Security provides some convenient annotations that are used to wrap your method calls in access checks.

代わりに、 Spring Security はいくつかの便利なアノテーションを提供しています。  
それらのアノテーションはアクセスチェックの中でメソッドの呼び出しをラップするのに使われます。

> There are four annotations:

4つのアノテーションがあります。

- `@PreAuthorize`
- `@PreFilter`
- `@PostAuthorize`
- `@PostFilter`

> The annotations use security-specific Spring expression language (SpEL) expressions - see the documentation for the available standard and method expressions.

アノテーションはセキュリティ固有の Spring 式言語（SpEL） の式を使用します。  
有効な標準やメソッド式についてドキュメントを見てください。

> Here’s an example service that manages a Report domain class and uses these annotations and expressions:

ここに、 `Report` ドメインクラスを管理し、アノテーションと式を使用するサービスの例を示します。

```groovy
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import grails.transaction.Transactional

import com.yourapp.Report

class ReportService {

   @PreAuthorize("hasPermission(#id, 'com.yourapp.Report', read) or " +
                 "hasPermission(#id, 'com.yourapp.Report', admin)")
   Report getReport(long id) {
      Report.get(id)
   }

   @Transactional
   @PreAuthorize("hasRole('ROLE_USER')")
   Report createReport(params) {
      Report report = new Report(params)
      report.save()
      report
   }

   @PreAuthorize("hasRole('ROLE_USER')")
   @PostFilter("hasPermission(filterObject, read) or " +
               "hasPermission(filterObject, admin)")
   List getAllReports(params = [:]) {
      Report.list(params)
   }

   @Secured(['ROLE_USER', 'ROLE_ADMIN'])
   String getReportName(long id) {
      Report.get(id).name
   }

   @Transactional
   @PreAuthorize("hasPermission(#report, write) or " +
                 "hasPermission(#report, admin)")
   Report updateReport(Report report, params) {
      report.properties = params
      report.save()
      report
   }

   @Transactional
   @PreAuthorize("hasPermission(#report, delete) or " +
                 "hasPermission(#report, admin)")
   void deleteReport(Report report) {
      report.delete()
   }
}
```

> The configuration specifies these rules:

設定はこれらのルールを指定しています。

- getReport requires that the authenticated user have BasePermission.READ or BasePermission.ADMIN for the instance
- createReport requires ROLE_USER
- getAllReports requires ROLE_USER and will have elements removed from the returned List that the user doesn’t have an ACL grant for; the user must have BasePermission.READ or BasePermission.ADMIN for each element in the list; elements that don’t have access granted will be removed
- getReportName requires that the authenticated user have either ROLE_USER or ROLE_ADMIN (but no ACL rules)
- updateReport has no role restrictions but must satisfy the requirements of the aclReportWriteVoter voter (which has the ACL_REPORT_WRITE config attribute), i.e. BasePermission.ADMINISTRATION or BasePermission.WRITE
- deleteReport has no role restrictions but must satisfy the requirements of the aclReportDeleteVoter voter (which has the ACL_REPORT_DELETE config attribute), i.e. BasePermission.ADMINISTRATION or BasePermission.DELETE

- `getReport` は認証されたユーザがインスタンスに対する `BasePermission.READ` か `BasePermission.ADMIN` を持つことを要求します。
- `createReport` は `ROLE_USER` を要求します
- `getAllReports` は `ROLE_USER` を要求し、 return されたリストのうち、ユーザに ACL が与えられていない要素が削除されます。  
  ユーザは `BasePermission.READ` か `BasePermission.ADMIN` をリストの各要素に対して持たなければなりません。  
  アクセスが許可されていない要素は削除されます。
- `getReportName` は認証されたユーザが `ROLE_USER` か `ROLE_ADMIN` を持つことを要求します（ACL ルールではありません）
- `updateReport` はロールによる制限を持ちません。しかし、 `aclReportWriteVoter` Voter による要求を満たす必要があります。  
  （voter は `ACL_REPORT_WRITE` 設定属性を持ちます）  
  つまり `BasePermission.ADMINISTRATION` または `BasePermission.WRITE` です。
- `deleteReport` はロールによる制約がありません。しかし、 `aclReportDeleteVoter` Voter による要求を満たす必要があります。  
  （voter は `ACL_REPORT_DELETE` 設定属性を持ちます）  
  つまり、 `BasePermission.ADMINISTRATION` または `BasePermission.DELETE` です。

## 2.3. Domain Classes
> The plugin uses domain classes to manage database state.

プラグインはドメインクラスをデータベースの状態を管理するために使用します。

> Ordinarily the database structure isn’t all that important, but to be compatible with the traditional JDBC-based Spring Security code, the domain classes are configured to generate the table and column names that are used there.

通常、データベースの構造は重要ではありません。しかし、伝統的な JDBC ベースの Spring Security コードと互換性を持たせるため、ドメインクラスは Spring Security のコードから利用できるようにテーブル名とカラム名を生成するよう設定されます。

> The plugin classes related to persistence use these classes, so they’re included in the plugin but can be overridden by running the s2-create-acl-domains script.

> As you can see, the database structure is highly normalized.

### 2.3.1. AclClass
> The AclClass domain class contains entries for the names of each application domain class that has associated permissions:

```groovy
package grails.plugin.springsecurity.acl

class AclClass {

	String className

	@Override
	String toString() {
		"AclClass id $id, className $className"
	}

	static mapping = {
		className column: 'class'
		version false
	}

	static constraints = {
		className unique: true, blank: false
	}
}
```

### 2.3.2. AclSid
> The AclSid domain class contains entries for the names of grant recipients (a principal or authority - SID is an acronym for “security identity”).

> These are typically usernames (where principal is true) but can also be a GrantedAuthority (role name, where principal is false).

> When granting permissions to a role, any user with that role receives that permission:

```groovy
package grails.plugin.springsecurity.acl

class AclSid {

	String sid
	boolean principal

	@Override
	String toString() {
		"AclSid id $id, sid $sid, principal $principal"
	}

	static mapping = {
		version false
	}

	static constraints = {
		principal unique: 'sid'
		sid blank: false, size: 1..255
	}
}
```

### 2.3.3. AclObjectIdentity
> The AclObjectIdentity domain class contains entries representing individual domain class instances (OIDs).

> It has a field for the instance id (objectId) and domain class (aclClass) that uniquely identify the instance.

> In addition there are optional nullable fields for the parent OID (parent) and owner (owner).

> There’s also a flag (entriesInheriting) to indicate whether ACL entries can inherit from a parent ACL.

```groovy
package grails.plugin.springsecurity.acl

class AclObjectIdentity extends AbstractAclObjectIdentity {

	Long objectId

	@Override
	String toString() {
		"AclObjectIdentity id $id, aclClass $aclClass.className, " +
		"objectId $objectId, entriesInheriting $entriesInheriting"
	}

	static mapping = {
		version false
		aclClass column: 'object_id_class'
		owner column: 'owner_sid'
		parent column: 'parent_object'
		objectId column: 'object_id_identity'
	}

	static constraints = {
		objectId unique: 'aclClass'
	}
}
```

> AclObjectIdentity actually extends a base class, AbstractAclObjectIdentity:

```groovy
abstract class AbstractAclObjectIdentity {

   AclClass aclClass
   AclObjectIdentity parent
   AclSid owner
   boolean entriesInheriting

   static constraints = {
      parent nullable: true
      owner nullable: true
   }
}
```

> By default it’s assumed that domain classes have a numeric primary key, but that’s not required.

> So the default implementation has a Long objectId field, but if you want to support other types of ids you can change that field and retain the other standard functionality from the base class.

### 2.3.4. AclEntry
> Finally, the AclEntry domain class contains entries representing grants (or denials) of a permission on an object instance to a recipient.

> The aclObjectIdentity field references the domain class instance (since an instance can have many granted permissions).

> The sid field references the recipient.

> The granting field determines whether the entry grants the permission (true) or denies it (false).

> The aceOrder field specifies the position of the entry, which is important because the entries are evaluated in order and the first matching entry determines whether access is allowed.

> auditSuccess and auditFailure determine whether to log success and/or failure events (these both default to false).

> The mask field holds the permission.

> This can be a source of confusion because the name (and the Spring Security documentation) indicates that it’s a bit mask.

> A value of 1 indicates permission A, a value of 2 indicates permission B, a value of 4 indicates permission C, a value of 8 indicates permission D, etc.

> So you would think that a value of 5 would indicate a grant of both permission A and C.

> Unfortunately this is not the case.

> There is a CumulativePermission class that supports this, but the standard classes don’t support it (AclImpl.isGranted() checks for == rather than using | (bitwise or) so a combined entry would never match).

> So rather than grouping all permissions for one recipient on one instances into a bit mask, you must create individual records for each.

```groovy
package grails.plugin.springsecurity.acl

class AclEntry {

	AclObjectIdentity aclObjectIdentity
	int aceOrder
	AclSid sid
	int mask
	boolean granting
	boolean auditSuccess
	boolean auditFailure

	@Override
	String toString() {
		"AclEntry id $id, aceOrder $aceOrder, mask $mask, granting $granting, " +
		"aclObjectIdentity $aclObjectIdentity"
	}

	static mapping = {
		version false
		sid column: 'sid'
		aclObjectIdentity column: 'acl_object_identity'
	}

	static constraints = {
		aceOrder unique: 'aclObjectIdentity'
	}
}
```