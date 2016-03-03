Pandora provides concurrent Collection,List,Set,Map,Queue,Increment and Rank backed by disk storage.
Pandora is free as speech and free as beer under [Apache License 2.0]

### Quick start

```java

PandoraMaker maker = new PandoraMaker("/data/pandora");
maker.createIfMissing(true);
Pandora pandora = maker.make();

List<byte[]> list = pandora.getList("list1");
list.add(...);


```
