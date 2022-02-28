import numpy as np

X_train = np.array([[6.8, 36.3,140.1,31,3.2,4],[7.6, 40.5, 141.4,0,4.6,5]])
X_train = X_train.reshape(2,3,2)

print(X_train)

Y_train = np.array([[1],[3]])
print(Y_train)