package io.ribot.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Name implements Parcelable {
    public String first;
    public String last;

    public Name() {
    }

    @Override
    public boolean equals(Object o) {
        //if関門壱：本インスタンスと、パラメータのインスタンスが == なら、true
        if (this == o) return true;
        //if関門弐：パラメータのインスタンスがnull
        //本インスタンスのクラスとパラメータのクラスが異なれば、false
        if (o == null || getClass() != o.getClass()) return false;

        //パラメータの Object を 本クラスにキャストして、ローカル変数 name に格納
        Name name = (Name) o;

        //if関門参：本インスタンスの二つあるフィールドと、
        // 本クラスにキャストしたパラメータとを比較する条件式にかける

        //フィールド1 がnullではないか。 true なら、本クラスの String フィールドと、
        //本クラスにキャストしたパラメータの同じフィールドとを equals にかける

        //フィールド1 が null の場合、パラメータの該当 String フィールドも同じく nullかどうかチェック
        if (first != null ? !first.equals(name.first) : name.first != null) return false;
        //上記条件式を、2つめのフィールドにもかける
        return !(last != null ? !last.equals(name.last) : name.last != null);
    }

    @Override
    public int hashCode() {
        //二つあるフィールドのうち一つ目が null の場合 0。
        // nullじゃなきゃ first.hashCode() の処理をする
        int result = first != null ? first.hashCode() : 0;
        //上記結果に 31 を乗算した結果に、
        //二つ目のフィールドに一つ目と同じ処理を行った結果を 加算する
        result = 31 * result + (last != null ? last.hashCode() : 0);
        //上記処理結果を hashCode とする
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.first);
        dest.writeString(this.last);
    }

    protected Name(Parcel in) {
        this.first = in.readString();
        this.last = in.readString();
    }

    public static final Parcelable.Creator<Name> CREATOR = new Parcelable.Creator<Name>() {
        public Name createFromParcel(Parcel source) {
            return new Name(source);
        }

        public Name[] newArray(int size) {
            return new Name[size];
        }
    };
}
