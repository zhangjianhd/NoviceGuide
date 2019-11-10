# Android无侵入式引导提示-NoviceGuide

## 优点

代码无侵入式，不需要处理原来的布局以及逻辑，只要在需要显示的地方像显示一个dialog一样配置好然后调用show方法即可

## 依赖方法

```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```groovy
dependencies {
	        implementation 'com.github.ZhangJian96:NoviceGuide:1.0.0'
	}
```

## api

| NoviceGuide.Builder的api                                     | 方法说明                                                     |
| ------------------------------------------------------------ | :----------------------------------------------------------- |
| focusView                                                    | 出现引导高亮显示的View(一般是引导描述的按钮等)               |
| setPadding                                                   | 设置高亮区域在View周边padding                                |
| setRadius                                                    | 设置高亮部分的圆角（默认0，就是矩形），当设置超过View半径就会是圆（类比drawable的Radius） |
| setRelyActivity                                              | 当前引导所依附的Activity（因为原理是拿到Activity的android.R.id.content。所以目前只支持对属于Activity的View做处理，这也是后期优化点） |
| setLayout(int layout,DecorateInflate decorateInflate)        | 设置引导显示提示的布局，内部处理好inflate的过程，同时提供DecorateInflate回调装饰inflate后的View，不需要可以传null |
| setPassId                                                    | 设置上方法提供的不居中“跳过”的按钮id，可不设置，不设置的时候需要自己在DecorateInflate回调中自己处理好调用dismiss()的逻辑。**注意**：在使用*NoviceGuideSet*构建一条链的引导时，请设置该方法，交给*NoviceGuideSet*内部自己去处理链的跳转步骤 |
| build                                                        | 返回NoviceGuide对象                                          |

| NoviceGuide | 方法说明                                       |
| ----------- | ---------------------------------------------- |
| show        | 将设置好的*NoviceGuide*显示出来                |
| dismiss     | 关闭引导，一般情况无需使用者调用，由内部处理好 |

| NoviceGuideSet                    | 方法说明                                   |
| --------------------------------- | ------------------------------------------ |
| addGuide(NoviceGuide NoviceGuide) | 添加引导链其中的一个步骤                   |
| show                              | 显示引导，点击PassView后按添加顺序依次展示 |

## 范例

### 创建一个引导:

```java
  new NoviceGuide.Builder(MainActivity.this)
                          .focusView(binding.btnGuide)
                          .setRadius(1000)    //显示出圆形
                          .setRelyActivity(MainActivity.this)
                          .setLayout(R.layout.layout_btn_guide, new DecorateInflate() {
                              @Override
                              public void onInflate(final NoviceGuide NoviceGuide, View inflaterView) {
                                  inflaterView.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          NoviceGuide.dismiss();
                                      }
                                  });
                              }
                          })
                          .build()
                          .show();
```

### 如果是一系列的步骤引导，你可以使用*NoviceGuideSet*

```java
NoviceGuide NoviceGuide1 = new NoviceGuide.Builder(MainActivity.this)
                .focusView(binding.tvBtn1)
                .setPadding(5, 5, 5, 5)
                .setRadius(15)
                .setRelyActivity(MainActivity.this)
                .setLayout(R.layout.layout_guide, null)
                .setPassId(R.id.iv_know)
                .build();

NoviceGuide NoviceGuide2 = new NoviceGuide.Builder(MainActivity.this)
                .focusView(binding.tvBtn2)
                .setPadding(5, 5, 5, 5)
                .setRadius(15)
                .setRelyActivity(MainActivity.this)
                .setLayout(R.layout.layout_guide, null)
                .setPassId(R.id.iv_know)
                .build();

NoviceGuideSet NoviceGuideSet = new NoviceGuideSet();
NoviceGuideSet.addGuide(NoviceGuide1);
NoviceGuideSet.addGuide(NoviceGuide2);
NoviceGuideSet.show();
```

 ## 效果

![提示引导](https://github.com/ZhangJian96/NoviceGuide/blob/master/exp.jpg?raw=true "提示引导")
![多步骤第一步](https://github.com/ZhangJian96/NoviceGuide/blob/master/exp_chain_0.jpg?raw=true "第一步")
![多步骤第二步](https://github.com/ZhangJian96/NoviceGuide/blob/master/exp_chain_1.jpg?raw=true "第二步")
