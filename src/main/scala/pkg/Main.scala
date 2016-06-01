package pkg

/**
  * Created by johnreed on 5/23/16.
  */
object Main {

  // foo1 through foo5 are the same

  def foo1[T](x : Array[T]) = println(x.length)

  def foo2(x : Array[T] forSome { type T}) = {
    println(x.length)
  }

  def foo3(
    x : Array[T] forSome { type T >: Nothing <: Any}
  ) = { println(x.length) }

  def foo4(x : Array[_]) = println(x.length) // curly braces are for multiple lines

  def foo5(x : Array[_ >: Nothing <: Any]) = {
    println(x.length)
  }

  // "extends AnyRef" is normally compiler generated. AnyRef extends Any.
  abstract class Animal extends AnyRef {
    def printAnimal(): Unit = println("Animal")
  }

  class Dog() extends Animal {
    def printDog(): Unit = println("Dog")
  }

  def main(args: Array[String]) {
    // In Scala, everything extends Any and nothing extends Nothing.
    val d1: Dog = new Dog()
    val d2: Animal = new Dog()
    val d3: AnyRef = new Dog()
    val d4: Any = new Dog()
    // val d5: Nothing = new Dog() // Illegal, concrete type Dog is not subclass of Nothing.

    // These are the same
    val range0: List[_ >: Nothing <: Any] = List(d1)
    val range00 : List[T] forSome {type T >: Nothing <: Any} = List(d1);

    val range1: List[_ >: Animal <: Any] = List(d1)
    val range2: List[_ >: Dog <: Animal] = List(d1)
    val range3: List[_ >: Nothing <: Dog] = List(d1)
    val range4: List[_ >: Dog <: Dog] = List(d1)
    // val range5: List[_ >: Nothing <: Nothing] = List(d1)
    // Not legal because d1 is not of type Nothing.

    // these are the same
    val getFunction0: (Int) => Any = range0.apply _
    val getFunction00: Function1[Int, Any] = range0.apply _

    // Existentials always make the safest guess (in this case most general return type)
    val getFunction1: (Int) => Any = range1.apply _
    val getFunction2: (Int) => Animal = range2.apply _
    val getFunction3: (Int) => Dog = range3.apply _
    val getFunction4: (Int) => Dog = range4.apply _

    // For adding, because we want to guarantee type safety, we require the most specific input type
    val appendFunction0: (Nothing) => List[Any] = range0.:: _
    val appendFunction1: (Animal) => List[Any] = range1.:: _
    val appendFunction2: (Dog) => List[Animal] = range2.:: _
    val appendFunction3: (Nothing) => List[Dog] = range3.:: _
    val appendFunction4: (Dog) => List[Dog] = range4.:: _

    // val val0 = range0.apply(0).printAnimal() // Illegal: Could be type Any, which cannot call printAnimal.
    // val val1 = range1(0).printAnimal() // Also illegal
    val val2 = range2(0).printAnimal()
    val val3 = range3(0).printAnimal()
    val val4 = range4(0).printAnimal()

    // val result1: Dog = range0(0) // illegal
    // val result2: Dog = range1(0)
    // val result3: Dog = range2(0)
    val result4: Dog = range3(0)
    val result5: Dog = range4(0)

    def eatDog(d: Dog): Unit = {
      println("This dog has been eaten")
    }

    // eatDog(range0(0)) // illegal, might not be a dog
    // eatDog(range1(0)) // illegal, might not be a dog
    // eatDog(range2(0)) // illegal, no supertypes of dog
    eatDog(range3(0)) // legal, dog or subtype of dog
    eatDog(range4(0)) // legal, is definitely a dog
  }
}
