import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;

import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;

/**
 * 金额表现类，用于表示系统中的金额及其计算逻辑的封装
 *
 * @version $Id: Money.java, v 1.0.1 2015年12月31日 下午2:05:04 huangkai Exp $
 *
 */
public class Money implements Serializable, Comparable<Money> {
    private static final long serialVersionUID = -1459240821831878390L;

    /** 金额, 精度到分 */
    private long amount;

    public Money() {}

    /** 通过分构造Money类 */
    public static Money ofCent(long cent) {
        Money money = new Money();
        money.amount = cent;
        return money;
    }

    /** 通过元构造Money类 */
    public static Money ofYuan(double yuan) {
        Money money = new Money();
        money.amount = convertYuanToCent(yuan);
        return money;
    }

    /** 金额转化为分 */
    public long getCent() {
        return getAmount();
    }

    /** 金额转化为元 */
    public double getYuan() {
        return convertCentToYuan(getAmount());
    }

    /**
     * 计算总金额
     *
     * @param monies 需要计算总金额的Money实例
     * @return 包含总金额的Money结果实例
     */
    public static Money total(Money... monies) {
        if (monies.length == 0) {
            throw new IllegalArgumentException("Money array must not be empty");
        }
        return total(Lists.newArrayList(monies));
    }

    /**
     * 计算总金额
     *
     * @param monies 需要计算总金额的Money实例
     * @return 包含总金额的Money结果实例
     */
    public static Money total(Iterable<? extends Money> monies) {
        checkNotNull(monies, "Money iterator must not be null");
        Iterator<? extends Money> it = monies.iterator();
        if (it.hasNext() == false) {
            throw new IllegalArgumentException("Money iterator must not be empty");
        }
        Money total = it.next();
        checkNotNull(total, "Money iterator must not contain null entries");
        while (it.hasNext()) {
            total = total.plus(it.next());
        }
        return total;
    }

    /**
     * 增加金额
     *
     * @param money 包含要增加的额度的Money实例
     * @return 增加完额度后的Money实例
     */
    public Money plus(Money money) {
        checkNotNull(money, "money must not be null");
        return plusCent(money.getCent());
    }

    /**
     * 以分的形式增加金额
     *
     * @param cent 要增加的额度，以分表示
     * @return 增加完额度后的Money实例
     */
    public Money plusCent(long cent) {
        this.amount += cent;
        return this;
    }

    /**
     * 以元的形式增加金额
     *
     * @param yuan 要增加的额度，以元表示
     * @return  增加完额度后的Money实例
     */
    public Money plusYuan(double yuan) {
        if(yuan == 0.0) { return this; }

        double total = convertCentToYuan(getAmount()) + yuan;
        this.amount = convertYuanToCent(total);
        return this;
    }

    /**
     * 减少金额
     *
     * @param money 包含要减少的额度的Money实例
     * @return 减少金额后的Money实例
     */
    public Money minus(Money money) {
        checkNotNull(money, "money must not be null");
        return minusCent(money.getCent());
    }

    /**
     * 以分的形式减少金额
     *
     * @param cent 要减少的额度，以分表示
     * @return 减少金额后的Money实例
     */
    public Money minusCent(long cent) {
        this.amount -= cent;
        return this;
    }

    /**
     * 以元的形式减少金额
     *
     * @param cent 要减少的额度，以元表示
     * @return 减少金额后的Money实例
     */
    public Money minusYuan(double yuan) {
        if (yuan == 0.0) { return this; }

        double result = convertCentToYuan(getAmount()) - yuan;
        this.amount = convertYuanToCent(result);
        return this;
    }

    /**
     * 当前额度做乘法计算
     *
     * @param valueToMultiplyBy 乘数
     * @return 进行乘法计算后的Moeny结果实例
     */
    public Money multipliedBy(double valueToMultiplyBy) {
        if (valueToMultiplyBy == 1) {
            return this;
        }

        double newAmountCent = getAmount() * valueToMultiplyBy;
        this.amount = BigDecimal.valueOf(newAmountCent)
                .setScale(0, RoundingMode.HALF_UP).longValue();
        return this;
    }

    /** 判断该Money实例的金额是否为负 */
    public boolean isNegative() {
        return getAmount() < 0;
    }

    /** 判断该Money实例的金额是否为负或者为零 */
    public boolean isNegativeOrZero() {
        return getAmount() <= 0;
    }

    /** 判断两个Money实例是否额度相等 */
    public boolean isEqual(Money other) {
        return compareTo(other) == 0;
    }

    /** 判断Money实例的金额是否大于要比较的Money实例 */
    public boolean isGreaterThan(Money other) {
        return compareTo(other) > 0;
    }

    /** 判断Money实例的金额是否小于要比较的Money实例 */
    public boolean isLessThan(Money other) {
        return compareTo(other) < 0;
    }

    /** 构造金额为零的Money实例  */
    public static Money zero() {
        return Money.ofCent(0);
    }

    /** 将金额从元转为分*/
    public static long convertYuanToCent(double yuan) {
        return BigDecimal.valueOf(yuan).movePointRight(2)
                .setScale(0, RoundingMode.HALF_UP).longValue();
    }

    /** 将金额从分转为元 */
    public static double convertCentToYuan(long cent) {
        return cent / 100.0;
    }

    private long getAmount() {
        return this.amount;
    }

    /** amount的单位为分 */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        return Longs.hashCode(amount);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Money) {
            Money otherMoney = (Money) other;
            return amount == otherMoney.amount;
        }
        return false;
    }

    @Override
    public int compareTo(Money otherMoney) {
        if(otherMoney == null) { return 1; }

        return Longs.compare(amount, otherMoney.amount);
    }

    @Override
    public String toString() {
        return getAmount() + " cent";
    }

}
