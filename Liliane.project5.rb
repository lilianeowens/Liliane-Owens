#### Liliane Owens
class BST
    attr_accessor :root
    # Node Structure within the BST for internal representation
    class Node
        attr_accessor :val, :left, :right
 def initialize(val)
    @val = val
    @left = nil
    @right = nil
 end
end
# Initialize a new BST with an optional comparison block
    def initialize(&compare_method)
        @root = nil
        @size = 0
        @compare_method = compare_method || proc {|a, b| a <=> b };

    end

    # Add an item to the BST, ensuring BST properties are maintained
    def add(item)
        @root = add_recursive(@root, item)
        @size += 1
      end
    # check if BST is empty
    def empty?
        @root.nil?
      end
    # Check if an item is included in the BST
  def include?(item)
    !!find(@root, item)
  end

  # Return the size of the BST
  def size
    @size
  end

  # In-order traversal, yielding each item to the given block
  def each_inorder(&block)
    each_inorder_recursive(@root, &block)
  end

  # Collect items with an in-order traversal, returning a new BST with modified items
  def collect_inorder(&block)
    new_bst = BST.new(&@compare_method)
    each_inorder { |item| new_bst.add(block.call(item)) }
    new_bst
  end

  # Convert the BST to a sorted array
  def to_a
    result = []
    each_inorder { |item| result << item }
    result
  end

  # Duplicate the BST with a deep copy
  def dup
    new_bst = BST.new(&@compare_method)
    each_inorder { |item| new_bst.add(item) }
    new_bst
  end

  private

  # Recursive helper to add an item in the BST
  def add_recursive(node, item)
    return Node.new(item) if node.nil?

    case @compare_method.call(item, node.val)
    when -1
      node.left = add_recursive(node.left, item)
    else
      node.right = add_recursive(node.right, item)
    end

    node
  end

  # Recursive helper to find an item in the BST
  def find(node, item)
    return nil if node.nil?

    case @compare_method.call(item, node.val)
    when 0
      node
    when -1
      find(node.left, item)
    else
      find(node.right, item)
    end
  end

  # Recursive helper for in-order traversal
  def each_inorder_recursive(node, &block)
    return if node.nil?

    each_inorder_recursive(node.left, &block)
    yield node.val
    each_inorder_recursive(node.right, &block)
  end
end

