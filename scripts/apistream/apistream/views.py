from apistream import models
from rest_framework import permissions, viewsets

from apistream.serializers import UserSerializer, OrdersSerializer, GoodSerializer, CartSerializer, OrderListSerializer


class UsrViewSet(viewsets.ModelViewSet):
    queryset = models.Usr.objects.all() 
    serializer_class = UserSerializer


class OrderViewSet(viewsets.ModelViewSet):
    queryset = models.Orders.objects.all()
    serializer_class = OrdersSerializer

class GoodViewSet(viewsets.ModelViewSet):
    queryset = models.Good.objects.all()
    serializer_class = GoodSerializer

class CartViewSet(viewsets.ModelViewSet):
    queryset = models.UsrGoodList.objects.all()
    serializer_class = CartSerializer

class OrderListViewSet(viewsets.ModelViewSet):
    queryset = models.OrdersGoodList.objects.all()
    serializer_class = OrderListSerializer
