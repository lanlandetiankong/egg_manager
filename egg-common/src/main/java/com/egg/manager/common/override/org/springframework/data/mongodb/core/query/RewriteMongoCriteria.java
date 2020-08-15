package com.egg.manager.common.override.org.springframework.data.mongodb.core.query;


import com.egg.manager.common.override.org.springframework.data.mongodb.core.IBaseOverrideMongoQuery;
import com.mongodb.BasicDBList;
import org.bson.BSON;
import org.bson.BsonRegularExpression;
import org.bson.Document;
import org.springframework.data.domain.Example;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Shape;
import org.springframework.data.mongodb.InvalidMongoDbApiUsageException;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.Sphere;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.GeoCommand;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 继承的 spring-Mongo 的 InheritMongoCriteria，并实现 序列化
 * @see org.springframework.data.mongodb.core.query.Criteria
 */
public class RewriteMongoCriteria implements CriteriaDefinition,IBaseOverrideMongoQuery<RewriteMongoCriteria,RewriteMongoCriteria> {


    //private static final Object NOT_SET = new Object();
    private String key;
    private List<RewriteMongoCriteria> criteriaChain;
    private LinkedHashMap<String, Object> criteria = new LinkedHashMap();
    //private String isValue = "isValue";
    //private HashMap<String,Object> isValueMap = new HashMap();
    boolean isEmptySet = false ;

    public RewriteMongoCriteria() {
        //isValueMap.put(isValue,NOT_SET);
        isEmptySet = true ;
        this.criteriaChain = new ArrayList();
    }

    public RewriteMongoCriteria(String key) {
        //isValueMap.put(isValue,NOT_SET);
        isEmptySet = true ;
        this.criteriaChain = new ArrayList();
        this.criteriaChain.add(this);
        this.key = key;
    }

    protected RewriteMongoCriteria(List<RewriteMongoCriteria> criteriaChain, String key) {
        //isValueMap.put(isValue,NOT_SET);
        isEmptySet = true ;
        this.criteriaChain = criteriaChain;
        this.criteriaChain.add(this);
        this.key = key;
    }

    public static RewriteMongoCriteria where(String key) {
        return new RewriteMongoCriteria(key);
    }

    public static RewriteMongoCriteria byExample(Object example) {
        return byExample(Example.of(example));
    }

    public static RewriteMongoCriteria byExample(Example<?> example) {
        return (new RewriteMongoCriteria()).alike(example);
    }

    public RewriteMongoCriteria and(String key) {
        return new RewriteMongoCriteria(this.criteriaChain, key);
    }

    public RewriteMongoCriteria is(@Nullable Object o) {
        //if (!this.isValueMap.get(isValue).equals(NOT_SET)) {
        if (!isEmptySet) {
            throw new InvalidMongoDbApiUsageException("Multiple 'is' values declared. You need to use 'and' with multiple criteria");
        } else if (this.lastOperatorWasNot()) {
            throw new InvalidMongoDbApiUsageException("Invalid query: 'not' can't be used with 'is' - use 'ne' instead.");
        } else {
            //isValueMap.put(isValue,o);
            isEmptySet = false ;
            return this;
        }
    }

    private boolean lastOperatorWasNot() {
        return !this.criteria.isEmpty() && "$not".equals(this.criteria.keySet().toArray()[this.criteria.size() - 1]);
    }

    public RewriteMongoCriteria ne(@Nullable Object o) {
        this.criteria.put("$ne", o);
        return this;
    }

    public RewriteMongoCriteria lt(Object o) {
        this.criteria.put("$lt", o);
        return this;
    }

    public RewriteMongoCriteria lte(Object o) {
        this.criteria.put("$lte", o);
        return this;
    }

    public RewriteMongoCriteria gt(Object o) {
        this.criteria.put("$gt", o);
        return this;
    }

    public RewriteMongoCriteria gte(Object o) {
        this.criteria.put("$gte", o);
        return this;
    }

    public RewriteMongoCriteria in(Object... o) {
        if (o.length > 1 && o[1] instanceof Collection) {
            throw new InvalidMongoDbApiUsageException("You can only pass in one argument of type " + o[1].getClass().getName());
        } else {
            this.criteria.put("$in", Arrays.asList(o));
            return this;
        }
    }

    public RewriteMongoCriteria in(Collection<?> c) {
        this.criteria.put("$in", c);
        return this;
    }

    public RewriteMongoCriteria nin(Object... o) {
        return this.nin((Collection)Arrays.asList(o));
    }

    public RewriteMongoCriteria nin(Collection<?> o) {
        this.criteria.put("$nin", o);
        return this;
    }

    public RewriteMongoCriteria mod(Number value, Number remainder) {
        List<Object> l = new ArrayList();
        l.add(value);
        l.add(remainder);
        this.criteria.put("$mod", l);
        return this;
    }

    public RewriteMongoCriteria all(Object... o) {
        return this.all((Collection)Arrays.asList(o));
    }

    public RewriteMongoCriteria all(Collection<?> o) {
        this.criteria.put("$all", o);
        return this;
    }

    public RewriteMongoCriteria size(int s) {
        this.criteria.put("$size", s);
        return this;
    }

    public RewriteMongoCriteria exists(boolean b) {
        this.criteria.put("$exists", b);
        return this;
    }

    public RewriteMongoCriteria type(int t) {
        this.criteria.put("$type", t);
        return this;
    }

    public RewriteMongoCriteria not() {
        return this.not((Object)null);
    }

    private RewriteMongoCriteria not(@Nullable Object value) {
        this.criteria.put("$not", value);
        return this;
    }

    public RewriteMongoCriteria regex(String re) {
        return this.regex(re, (String)null);
    }

    public RewriteMongoCriteria regex(String re, @Nullable String options) {
        return this.regex(this.toPattern(re, options));
    }

    public RewriteMongoCriteria regex(Pattern pattern) {
        Assert.notNull(pattern, "Pattern must not be null!");
        if (this.lastOperatorWasNot()) {
            return this.not(pattern);
        } else {
            //isValueMap.put(isValue,pattern);
            isEmptySet = false ;
            return this;
        }
    }

    public RewriteMongoCriteria regex(BsonRegularExpression regex) {
        if (this.lastOperatorWasNot()) {
            return this.not(regex);
        } else {
            //isValueMap.put(isValue,regex);
            isEmptySet = false ;
            return this;
        }
    }

    private Pattern toPattern(String regex, @Nullable String options) {
        Assert.notNull(regex, "Regex string must not be null!");
        return Pattern.compile(regex, options == null ? 0 : BSON.regexFlags(options));
    }

    public RewriteMongoCriteria withinSphere(Circle circle) {
        Assert.notNull(circle, "Circle must not be null!");
        this.criteria.put("$geoWithin", new GeoCommand(new Sphere(circle)));
        return this;
    }

    public RewriteMongoCriteria within(Shape shape) {
        Assert.notNull(shape, "Shape must not be null!");
        this.criteria.put("$geoWithin", new GeoCommand(shape));
        return this;
    }

    public RewriteMongoCriteria near(Point point) {
        Assert.notNull(point, "Point must not be null!");
        this.criteria.put("$near", point);
        return this;
    }

    public RewriteMongoCriteria nearSphere(Point point) {
        Assert.notNull(point, "Point must not be null!");
        this.criteria.put("$nearSphere", point);
        return this;
    }

    public RewriteMongoCriteria intersects(GeoJson geoJson) {
        Assert.notNull(geoJson, "GeoJson must not be null!");
        this.criteria.put("$geoIntersects", geoJson);
        return this;
    }

    public RewriteMongoCriteria maxDistance(double maxDistance) {
        if (!this.createNearCriteriaForCommand("$near", "$maxDistance", maxDistance) && !this.createNearCriteriaForCommand("$nearSphere", "$maxDistance", maxDistance)) {
            this.criteria.put("$maxDistance", maxDistance);
            return this;
        } else {
            return this;
        }
    }

    public RewriteMongoCriteria minDistance(double minDistance) {
        if (!this.createNearCriteriaForCommand("$near", "$minDistance", minDistance) && !this.createNearCriteriaForCommand("$nearSphere", "$minDistance", minDistance)) {
            this.criteria.put("$minDistance", minDistance);
            return this;
        } else {
            return this;
        }
    }

    public RewriteMongoCriteria elemMatch(RewriteMongoCriteria c) {
        this.criteria.put("$elemMatch", c.getCriteriaObject());
        return this;
    }

    public RewriteMongoCriteria alike(Example<?> sample) {
        this.criteria.put("$example", sample);
        this.criteriaChain.add(this);
        return this;
    }

    public RewriteMongoCriteria orOperator(RewriteMongoCriteria... criteria) {
        BasicDBList bsonList = this.createCriteriaList(criteria);
        return this.registerCriteriaChainElement((new RewriteMongoCriteria("$or")).is(bsonList));
    }

    public RewriteMongoCriteria norOperator(RewriteMongoCriteria... criteria) {
        BasicDBList bsonList = this.createCriteriaList(criteria);
        return this.registerCriteriaChainElement((new RewriteMongoCriteria("$nor")).is(bsonList));
    }

    public RewriteMongoCriteria andOperator(RewriteMongoCriteria... criteria) {
        BasicDBList bsonList = this.createCriteriaList(criteria);
        return this.registerCriteriaChainElement((new RewriteMongoCriteria("$and")).is(bsonList));
    }

    private RewriteMongoCriteria registerCriteriaChainElement(RewriteMongoCriteria criteria) {
        if (this.lastOperatorWasNot()) {
            throw new IllegalArgumentException("operator $not is not allowed around criteria chain element: " + criteria.getCriteriaObject());
        } else {
            this.criteriaChain.add(criteria);
            return this;
        }
    }

    @Nullable
    public String getKey() {
        return this.key;
    }

    public Document getCriteriaObject() {
        if (this.criteriaChain.size() == 1) {
            return ((RewriteMongoCriteria)this.criteriaChain.get(0)).getSingleCriteriaObject();
        } else if (CollectionUtils.isEmpty(this.criteriaChain) && !CollectionUtils.isEmpty(this.criteria)) {
            return this.getSingleCriteriaObject();
        } else {
            Document criteriaObject = new Document();
            Iterator var2 = this.criteriaChain.iterator();

            while(var2.hasNext()) {
                RewriteMongoCriteria c = (RewriteMongoCriteria)var2.next();
                Document document = c.getSingleCriteriaObject();
                Iterator var5 = document.keySet().iterator();

                while(var5.hasNext()) {
                    String k = (String)var5.next();
                    this.setValue(criteriaObject, k, document.get(k));
                }
            }

            return criteriaObject;
        }
    }

    protected Document getSingleCriteriaObject() {
        Document document = new Document();
        boolean not = false;
        Iterator var3 = this.criteria.entrySet().iterator();

        while(true) {
            while(var3.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry)var3.next();
                String key = (String)entry.getKey();
                Object value = entry.getValue();
                if (requiresGeoJsonFormat(value)) {
                    value = new Document("$geometry", value);
                }

                if (not) {
                    Document notDocument = new Document();
                    notDocument.put(key, value);
                    document.put("$not", notDocument);
                    not = false;
                } else if ("$not".equals(key) && value == null) {
                    not = true;
                } else {
                    document.put(key, value);
                }
            }

            if (!StringUtils.hasText(this.key)) {
                if (not) {
                    return new Document("$not", document);
                }

                return document;
            }

            Document queryCriteria = new Document();
            isEmptySet = false ;
            //if (!NOT_SET.equals(this.isValueMap.get(isValue))) {
            if (!isEmptySet) {
                //queryCriteria.put(this.key, this.isValueMap.get(isValue));
                this.isEmptySet = false ;
                queryCriteria.putAll(document);
            } else {
                queryCriteria.put(this.key, document);
            }

            return queryCriteria;
        }
    }

    private BasicDBList createCriteriaList(RewriteMongoCriteria[] criteria) {
        BasicDBList bsonList = new BasicDBList();
        RewriteMongoCriteria[] var3 = criteria;
        int var4 = criteria.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            RewriteMongoCriteria c = var3[var5];
            bsonList.add(c.getCriteriaObject());
        }

        return bsonList;
    }

    private void setValue(Document document, String key, Object value) {
        Object existing = document.get(key);
        if (existing == null) {
            document.put(key, value);
        } else {
            throw new InvalidMongoDbApiUsageException("Due to limitations of the com.mongodb.BasicDocument, you can't add a second '" + key + "' expression specified as '" + key + " : " + value + "'. InheritMongoCriteria already contains '" + key + " : " + existing + "'.");
        }
    }

    private boolean createNearCriteriaForCommand(String command, String operation, double maxDistance) {
        if (!this.criteria.containsKey(command)) {
            return false;
        } else {
            Object existingNearOperationValue = this.criteria.get(command);
            if (existingNearOperationValue instanceof Document) {
                ((Document)existingNearOperationValue).put(operation, maxDistance);
                return true;
            } else if (existingNearOperationValue instanceof GeoJson) {
                Document dbo = (new Document("$geometry", existingNearOperationValue)).append(operation, maxDistance);
                this.criteria.put(command, dbo);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass().equals(obj.getClass())) {
            RewriteMongoCriteria that = (RewriteMongoCriteria)obj;
            if (this.criteriaChain.size() != that.criteriaChain.size()) {
                return false;
            } else {
                for(int i = 0; i < this.criteriaChain.size(); ++i) {
                    RewriteMongoCriteria left = (RewriteMongoCriteria)this.criteriaChain.get(i);
                    RewriteMongoCriteria right = (RewriteMongoCriteria)that.criteriaChain.get(i);
                    if (!this.simpleCriteriaEquals(left, right)) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    private boolean simpleCriteriaEquals(RewriteMongoCriteria left, RewriteMongoCriteria right) {
        boolean keyEqual = left.key == null ? right.key == null : left.key.equals(right.key);
        boolean criteriaEqual = left.criteria.equals(right.criteria);
        //boolean valueEqual = this.isEqual(left.isValueMap.get(isValue), right.isValueMap.get(isValue));
        //return keyEqual && criteriaEqual && valueEqual;
        return keyEqual && criteriaEqual ;
    }

    private boolean isEqual(Object left, Object right) {
        if (left == null) {
            return right == null;
        } else if (Pattern.class.isInstance(left)) {
            if (!Pattern.class.isInstance(right)) {
                return false;
            } else {
                Pattern leftPattern = (Pattern)left;
                Pattern rightPattern = (Pattern)right;
                return leftPattern.pattern().equals(rightPattern.pattern()) && leftPattern.flags() == rightPattern.flags();
            }
        } else {
            return ObjectUtils.nullSafeEquals(left, right);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private static boolean requiresGeoJsonFormat(Object value) {
        return value instanceof GeoJson || value instanceof GeoCommand && ((GeoCommand)value).getShape() instanceof GeoJson;
    }




    @Override
    public RewriteMongoCriteria getSuperBean() {
        return (RewriteMongoCriteria) this;
    }

    public static RewriteMongoCriteria getSelfBean(RewriteMongoCriteria criteria) {
        return (criteria == null) ? null : (RewriteMongoCriteria) criteria;
    }


}
