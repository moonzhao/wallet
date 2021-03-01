# Some thinking of this code test

### Controller part (Rest API)
Two Controller is created and Swagger is used for REST API doc:

* AccountController is focus on operation on an Account.
* TransactionController is focus on transaction operation of an Account.
* transaction id is provided by user in this test as a simple solution, in a real product it should be generated on server by a specific solution.

### Service part
There are some alternative solution:

* For method debit and credit: 
  1. Use Transactional to make the DB operation Atomicity. 
  2. Use Lock on Repository to keep correction of Concurrency. 
  3. Checking unique transaction id to make keep Idempotency.
* For method getTransactionHistory, in order to keep limit items returned, there can be several solutions, such as limit begin and end date distance no more than 2 years.
  Or using page and limit to return limit items of transaction history

### Test

* Postman is used to do the REST API test.
* Jmeter is used to do the Concurrent access test.
* WebMvcTest is used for REST API unit test, also Rest Assured can be used. More or less the same.
* Junit5 and Mockito are also used in UT.

### DB

* H2 is used as DB solution.
* DB will be initialized by data.sql.
