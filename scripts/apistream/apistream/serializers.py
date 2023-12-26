from apistream import models
from rest_framework import serializers

class UsrSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = models.Usr
        fields = '__all__'

class OrdersSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = models.Orders
        fields = '__all__'

class GoodSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = models.Good
        fields = '__all__'

class CartSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = models.UsrGoodList
        fields = '__all__'

class OrderListSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = models.OrdersGoodList
        fields = '__all__'