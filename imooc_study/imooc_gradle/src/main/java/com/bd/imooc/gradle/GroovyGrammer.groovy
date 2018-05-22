import com.sun.corba.se.spi.orbutil.closure.Closure

public class ProjectVersion {
    /**
     * 自动生成getter/setter
     */
    private int major
    private int minor

    public ProjectVersion(int major, int minor) {
        //结尾不需要分号
        this.major = major
        this.minor = minor
    }
}

ProjectVersion v1 = new ProjectVersion(1, 1)
//直接访问成员变量
println v1.minor

ProjectVersion v2 = null
//==等于equal()
//println是方法，括号可选
println v1 == v2

//类型定义可选，自动推断
def version = 1
//assert version == 2

def str1 = 'imooc'
//可插入变量
def str2 = "gradle version is ${version}"
def str3 = '''my name
is imooc'''
println str2
println str3

/*
    集合类型
 */
def buildTools = ['maven']
buildTools << 'gradle'
assert buildTools.getClass() == ArrayList
assert buildTools.size() == 2

def buildYears = ['maven': 2004]
buildYears.gradle = 2009
println buildYears.gradle
println buildYears.getClass()

/*
    闭包
 */
def closure1 = {
    v -> println v
}

def closure2 = {
    println 'hello groovy'
}

def method1(Closure closure) {
    closure('param')
}

def method2(Closure closure) {
    closure
}

method1(closure1)
method2(closure2)