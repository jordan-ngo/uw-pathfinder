package sets;

import java.util.List;

/**
 * Represents an immutable set of points on the real line that is easy to
 * describe, either because it is a finite set, e.g., {p1, p2, ..., pN}, or
 * because it excludes only a finite set, e.g., R \ {p1, p2, ..., pN}. As with
 * FiniteSet, each point is represented by a Java float with a non-infinite,
 * non-NaN value.
 */
public class SimpleSet {

  // RI: -infinity < finiteSet.of(vals) < infinity
  // IR: OR R \ {FiniteSet.vals}
  // AF: {vals[1], vals[2], ..., vals[vals.length-2]}

  // For our two private fields we have our FiniteSet which is used to store vals and boolean
  // is used to check if the set is complementary or not. In short, FiniteSet is used to represent points
  // in a complementary set and points in a simple set. Moving on, we have our public SimpleSet which
  // uses "set" so that when initialized the IR

  private FiniteSet set;
  private boolean simpSetTF;

  /**
   * Creates a simple set containing only the given points.
   *
   * @param vals Array containing the points to make into a SimpleSet
   * @spec.requires points != null and has no NaNs, no infinities, and no dups
   * @spec.effects this = {vals[0], vals[1], ..., vals[vals.length-1]}
   */
  public SimpleSet(float[] vals) {
    this.set = FiniteSet.of(vals);
  }

  /**
   * Private constructor that directly fills in the fields above.
   *
   * @param complement Whether this = points or this = R \ points
   * @param points     List of points that are in the set or not in the set
   * @spec.requires points != null
   * @spec.effects this = R \ points if complement else points
   */
  private SimpleSet(boolean complement, FiniteSet points) {
    this.simpSetTF = complement;
    this.set = (points);

  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SimpleSet))
      return false;
    SimpleSet other = (SimpleSet) o;
    if (this.simpSetTF != other.simpSetTF) {
      return false;
    } else {
      return this.set.equals(other.set);
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Returns the number of points in this set.
   *
   * @return N      if this = {p1, p2, ..., pN} and
   * infty  if this = R \ {p1, p2, ..., pN}
   */
  public float size() {
    // TODO: implement this
    if (this.simpSetTF == true) {
      return Float.POSITIVE_INFINITY;
    } else {
      return this.set.size();
    }
  }

  /**
   * Returns a string describing the points included in this set.
   *
   * @return the string "R" if this contains every point,
   * a string of the form "R \ {p1, p2, .., pN}" if this contains all
   * but {@literal N > 0} points, or
   * a string of the form "{p1, p2, .., pN}" if this contains
   * {@literal N >= 0} points,
   * where p1, p2, ... pN are replaced by the individual numbers.
   */
  public String toString() {
    // TODO: implement this with a loop. document its invariant
    //       a StringBuilder may be useful for creating the string
    if (this.simpSetTF == true && this.size() == 0) {
      return "R";
    } else if (this.simpSetTF == true){

    } else if (this.simpSetTF){

    } else if (!this.simpSetTF && this.set.size()==0){
      return "{0}";
    }
    return "";
  }

  /**
   * Returns a set representing the points R \ this.
   *
   * @return R \ this
   */
  public SimpleSet complement() {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct (hint: cases)
  if(this.simpSetTF == true) {
    return new SimpleSet(false, this.set);

  }else{
    return new SimpleSet(true, this.set);

    }
  }

  /**
   * Returns the union of this and other.
   *
   * @param other Set to union with this one.
   * @return this union other
   * @spec.requires other != null
   */
  public SimpleSet union(SimpleSet other) {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct (hint: cases)
    if (this.simpSetTF == false && other.simpSetTF == false) {
      return new SimpleSet(false, this.set.union(other.set));

    } else if (this.simpSetTF == true && other.simpSetTF == true) {
      return new SimpleSet(true, this.set.intersection(other.set));

    } else if (this.simpSetTF == true && other.simpSetTF == false) {
      return new SimpleSet(true, this.set.difference(other.set));
    } else {
      return new SimpleSet(true, other.set.difference(this.set));
    }
  }

  /**
   * Returns the intersection of this and other.
   *
   * @param other Set to intersect with this one.
   * @return this intersect other
   * @spec.requires other != null
   */
  public SimpleSet intersection(SimpleSet other) {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct
    // NOTE: There is more than one correct way to implement this.
    if (this.simpSetTF == false && other.simpSetTF == false) {
      return new SimpleSet(false, this.set.intersection(other.set));

    } else if (this.simpSetTF == true && other.simpSetTF == true) {
      return new SimpleSet(true, this.set.union(other.set));

    } else if (this.simpSetTF == true && other.simpSetTF == false) {
      return new SimpleSet(false, other.set.difference(this.set));

    } else {
      return new SimpleSet(false, this.set.difference(other.set));
    }
  }

  /**
   * Returns the difference of this and other.
   *
   * @param other Set to difference from this one.
   * @return this minus other
   * @spec.requires other != null
   */
  public SimpleSet difference(SimpleSet other) {
    // TODO: implement this method
    //       include sufficient comments to see why it is correct
    // NOTE: There is more than one correct way to implement this.
    if (this.simpSetTF == false && other.simpSetTF == false) {
      return new SimpleSet(false, this.set.difference(other.set));

    } else if (this.simpSetTF == true && other.simpSetTF == true) {
      return new SimpleSet(false, other.set.difference(this.set));

    } else if (this.simpSetTF == true && other.simpSetTF == false) {
      return new SimpleSet(true, other.set.union(this.set));

    } else {
      return new SimpleSet(false, this.set.intersection(other.set));
    }
  }
}
